package com.jobtrackai.feature.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.entity.InterviewAnswerEntity
import com.jobtrackai.core.database.entity.InterviewQuestionEntity
import com.jobtrackai.core.database.entity.InterviewSessionEntity
import com.jobtrackai.feature.ai.domain.model.AIAnalysis
import com.jobtrackai.feature.ai.domain.service.AIService
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AIInterviewViewModel @Inject constructor(
    private val aiService: AIService,
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val aiDao: AIDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AIInterviewUiState())
    val uiState = _uiState.asStateFlow()

    fun startSession(role: String, level: String, count: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(status = SessionStatus.Generating) }
            
            val result = aiService.generateInterviewQuestions(role, level, "Normal", count)
            when (result) {
                is DomainResult.Success -> {
                    val sessionId = UUID.randomUUID().toString()
                    val user = getAuthStateUseCase().first()
                    
                    if (user != null) {
                        val session = InterviewSessionEntity(
                            sessionId = sessionId,
                            userId = user.id,
                            role = role,
                            level = level,
                            date = Instant.now()
                        )
                        val questions = result.data.mapIndexed { index, text ->
                            InterviewQuestionEntity(sessionId = sessionId, text = text, order = index)
                        }
                        
                        aiDao.insertSession(session)
                        aiDao.insertQuestions(questions)
                        
                        // Load the first question
                        val firstQuestion = aiDao.getQuestionsForSession(sessionId).first()
                        
                        _uiState.update { it.copy(
                            sessionId = sessionId,
                            questions = questions,
                            currentQuestionIndex = 0,
                            currentQuestion = firstQuestion,
                            status = SessionStatus.Answering
                        )}
                    }
                }
                is DomainResult.Error -> {
                    _uiState.update { it.copy(status = SessionStatus.Error(result.error.debugMessage ?: "Failed to generate questions")) }
                }
            }
        }
    }

    fun submitAnswer(answer: String) {
        val sessionId = _uiState.value.sessionId ?: return
        val currentQuestion = _uiState.value.currentQuestion ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isEvaluating = true) }
            
            val result = aiService.evaluateAnswer(currentQuestion.text, answer)
            when (result) {
                is DomainResult.Success -> {
                    val analysis = result.data
                    val answerEntity = InterviewAnswerEntity(
                        sessionId = sessionId,
                        questionId = currentQuestion.id,
                        answerText = answer,
                        technicalScore = analysis.technicalScore,
                        communicationScore = analysis.communicationScore,
                        completenessScore = analysis.completenessScore,
                        feedback = analysis.improvements.joinToString("\n"),
                        suggestedAnswer = analysis.suggestedAnswer
                    )
                    aiDao.insertAnswer(answerEntity)

                    val nextIndex = _uiState.value.currentQuestionIndex + 1
                    if (nextIndex < _uiState.value.questions.size) {
                        val questionsFromDb = aiDao.getQuestionsForSession(sessionId)
                        _uiState.update { it.copy(
                            currentQuestionIndex = nextIndex,
                            currentQuestion = questionsFromDb[nextIndex],
                            isEvaluating = false
                        )}
                    } else {
                        _uiState.update { it.copy(status = SessionStatus.Completed, isEvaluating = false) }
                    }
                }
                is DomainResult.Error -> {
                    _uiState.update { it.copy(isEvaluating = false) }
                }
            }
        }
    }
}

data class AIInterviewUiState(
    val status: SessionStatus = SessionStatus.Setup,
    val sessionId: String? = null,
    val questions: List<InterviewQuestionEntity> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val currentQuestion: InterviewQuestionEntity? = null,
    val isEvaluating: Boolean = false
)

sealed interface SessionStatus {
    data object Setup : SessionStatus
    data object Generating : SessionStatus
    data object Answering : SessionStatus
    data object Completed : SessionStatus
    data class Error(val message: String) : SessionStatus
}

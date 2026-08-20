package com.jobtrackai.feature.ai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.entity.InterviewAnswerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AIResultViewModel @Inject constructor(
    private val aiDao: AIDao
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<InterviewAnswerEntity>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadResults(sessionId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val answers = aiDao.getAnswersForSession(sessionId)
            _uiState.value = if (answers.isEmpty()) UiState.Empty else UiState.Success(answers)
        }
    }
}

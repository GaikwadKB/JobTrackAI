package com.jobtrackai.feature.interviews.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.usecase.ScheduleInterviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddInterviewViewModel @Inject constructor(
    private val scheduleInterviewUseCase: ScheduleInterviewUseCase,
    private val getAuthStateUseCase: GetAuthStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddInterviewUiState())
    val uiState = _uiState.asStateFlow()

    fun onTypeChanged(type: String) {
        _uiState.update { it.copy(type = type) }
    }

    fun onDateChanged(timestamp: Long) {
        _uiState.update { it.copy(scheduledAt = Instant.ofEpochMilli(timestamp)) }
    }

    fun onInterviewerChanged(name: String) {
        _uiState.update { it.copy(interviewerName = name) }
    }

    fun onMeetingUrlChanged(url: String) {
        _uiState.update { it.copy(meetingUrl = url) }
    }

    fun onNotesChanged(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun saveInterview(applicationId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            
            val user = getAuthStateUseCase().first()
            if (user == null) {
                _uiState.update { it.copy(isSaving = false) }
                return@launch
            }

            val interview = Interview(
                id = UUID.randomUUID().toString(),
                applicationId = applicationId,
                userId = user.id,
                type = _uiState.value.type,
                scheduledAt = _uiState.value.scheduledAt,
                meetingUrl = _uiState.value.meetingUrl.takeIf { it.isNotBlank() },
                interviewerName = _uiState.value.interviewerName.takeIf { it.isNotBlank() },
                notes = _uiState.value.notes.takeIf { it.isNotBlank() }
            )

            val result = scheduleInterviewUseCase(interview)
            _uiState.update { it.copy(isSaving = false, isSuccess = result is DomainResult.Success) }
        }
    }
}

data class AddInterviewUiState(
    val type: String = "Technical",
    val scheduledAt: Instant = Instant.now(),
    val interviewerName: String = "",
    val meetingUrl: String = "",
    val notes: String = "",
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false
)

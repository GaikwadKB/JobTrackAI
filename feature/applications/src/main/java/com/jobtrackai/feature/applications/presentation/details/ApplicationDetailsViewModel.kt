package com.jobtrackai.feature.applications.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationDetailsViewModel @Inject constructor(
    private val repository: ApplicationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Application>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadApplication(applicationId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getApplications("").collect { apps -> // This is a bit inefficient, should have getById
                val app = apps.find { it.id == applicationId }
                if (app != null) {
                    _uiState.value = UiState.Success(app)
                } else {
                    _uiState.value = UiState.Error(com.jobtrackai.core.common.result.DomainError.NotFound("Application not found"))
                }
            }
        }
    }

    fun updateStage(newStage: ApplicationStage) {
        val currentApp = (uiState.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            repository.updateApplicationStage(currentApp.id, newStage)
        }
    }

    fun updateNotes(notes: String) {
        val currentApp = (uiState.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            repository.updateApplicationDetails(currentApp.copy(notes = notes))
        }
    }
}

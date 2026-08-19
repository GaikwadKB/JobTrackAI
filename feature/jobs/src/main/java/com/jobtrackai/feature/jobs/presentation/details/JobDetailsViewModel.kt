package com.jobtrackai.feature.jobs.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.usecase.GetJobDetailsUseCase
import com.jobtrackai.feature.jobs.domain.usecase.ToggleSaveJobUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val toggleSaveJobUseCase: ToggleSaveJobUseCase
) : ViewModel() {

    private val _jobState = MutableStateFlow<UiState<Job>>(UiState.Loading)
    val jobState = _jobState.asStateFlow()

    fun loadJob(jobId: String) {
        viewModelScope.launch {
            _jobState.value = UiState.Loading
            val result = getJobDetailsUseCase(jobId)
            _jobState.value = when (result) {
                is DomainResult.Success -> UiState.Success(result.data)
                is DomainResult.Error -> UiState.Error(result.error)
            }
        }
    }

    fun toggleSave() {
        val currentJob = (jobState.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            toggleSaveJobUseCase(currentJob)
            _jobState.update { state ->
                if (state is UiState.Success) {
                    UiState.Success(state.data.copy(isSaved = !state.data.isSaved))
                } else state
            }
        }
    }
}

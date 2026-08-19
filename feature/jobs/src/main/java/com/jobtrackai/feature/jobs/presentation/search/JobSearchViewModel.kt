package com.jobtrackai.feature.jobs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.usecase.SearchJobsUseCase
import com.jobtrackai.feature.jobs.domain.usecase.ToggleSaveJobUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobSearchViewModel @Inject constructor(
    private val searchJobsUseCase: SearchJobsUseCase,
    private val toggleSaveJobUseCase: ToggleSaveJobUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _jobsState = MutableStateFlow<UiState<List<Job>>>(UiState.Idle)
    val jobsState = _jobsState.asStateFlow()

    private var currentPage = 1

    init {
        setupSearchDebounce()
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchDebounce() {
        _searchQuery
            .debounce(500L) // Rule 42
            .onEach { query ->
                currentPage = 1
                searchJobs(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun searchJobs(query: String) {
        viewModelScope.launch {
            _jobsState.value = UiState.Loading
            val result = searchJobsUseCase(query, currentPage)
            _jobsState.value = when (result) {
                is DomainResult.Success -> {
                    if (result.data.isEmpty()) UiState.Empty else UiState.Success(result.data)
                }
                is DomainResult.Error -> UiState.Error(result.error)
            }
        }
    }

    fun toggleSaveJob(job: Job) {
        viewModelScope.launch {
            toggleSaveJobUseCase(job)
            // Ideally we observe saved jobs and update the list, but for now we re-trigger search
            // or update the item in the list directly for immediate UI feedback.
            _jobsState.update { currentState ->
                if (currentState is UiState.Success) {
                    val updatedList = currentState.data.map {
                        if (it.id == job.id) it.copy(isSaved = !it.isSaved) else it
                    }
                    UiState.Success(updatedList)
                } else currentState
            }
        }
    }
}

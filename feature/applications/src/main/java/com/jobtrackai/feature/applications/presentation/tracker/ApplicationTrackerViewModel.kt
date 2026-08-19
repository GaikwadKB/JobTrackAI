package com.jobtrackai.feature.applications.presentation.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.domain.usecase.GetApplicationsUseCase
import com.jobtrackai.feature.applications.domain.usecase.UpdateApplicationStageUseCase
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ApplicationTrackerViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val getApplicationsUseCase: GetApplicationsUseCase,
    private val updateApplicationStageUseCase: UpdateApplicationStageUseCase,
    private val applyToJobUseCase: com.jobtrackai.feature.applications.domain.usecase.ApplyToJobUseCase
) : ViewModel() {

    val uiState: StateFlow<UiState<Map<ApplicationStage, List<Application>>>> = getAuthStateUseCase()
        .flatMapLatest { user ->
            if (user != null) {
                getApplicationsUseCase(user.id).map { apps ->
                    val grouped = apps.groupBy { it.stage }
                    val fullMap = ApplicationStage.entries.associateWith { stage ->
                        grouped[stage] ?: emptyList()
                    }
                    UiState.Success(fullMap)
                }
            } else {
                flowOf(UiState.Error(com.jobtrackai.core.common.result.DomainError.Unauthorized("Not logged in")))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun applyToJob(job: com.jobtrackai.feature.jobs.domain.model.Job) {
        viewModelScope.launch {
            val user = getAuthStateUseCase().first()
            if (user != null) {
                applyToJobUseCase(job, user.id)
            }
        }
    }

    fun moveApplication(applicationId: String, newStage: ApplicationStage) {
        viewModelScope.launch {
            updateApplicationStageUseCase(applicationId, newStage)
        }
    }
}

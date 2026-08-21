package com.jobtrackai.feature.analytics.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.model.AnalyticsState
import com.jobtrackai.core.common.model.DashboardSummary
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.analytics.domain.usecase.GetAnalyticsUseCase
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : ViewModel() {

    val summaryState: StateFlow<UiState<DashboardSummary>> = getAuthStateUseCase()
        .flatMapLatest { user ->
            if (user != null) {
                getAnalyticsUseCase.getSummary(user.id).map { UiState.Success(it) }
            } else {
                flowOf(UiState.Error(com.jobtrackai.core.common.result.DomainError.Unauthorized("Not logged in")))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    val statsState: StateFlow<UiState<AnalyticsState>> = getAuthStateUseCase()
        .flatMapLatest { user ->
            if (user != null) {
                getAnalyticsUseCase.getStats(user.id).map { UiState.Success(it) }
            } else {
                flowOf(UiState.Error(com.jobtrackai.core.common.result.DomainError.Unauthorized("Not logged in")))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )
}

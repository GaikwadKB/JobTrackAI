package com.jobtrackai.feature.interviews.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.usecase.GetInterviewsUseCase
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
class InterviewListViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val getInterviewsUseCase: GetInterviewsUseCase
) : ViewModel() {

    val uiState: StateFlow<UiState<List<Interview>>> = getAuthStateUseCase()
        .flatMapLatest { user ->
            if (user != null) {
                getInterviewsUseCase(user.id).map { interviews ->
                    if (interviews.isEmpty()) UiState.Empty else UiState.Success(interviews)
                }
            } else {
                flowOf(UiState.Error(DomainError.Unauthorized("Not logged in")))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )
}

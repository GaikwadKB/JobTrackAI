package com.jobtrackai.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase
) : ViewModel() {

    val uiState = getAuthStateUseCase()
        .map { user ->
            if (user != null) MainUiState.Authenticated else MainUiState.Unauthenticated
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState.Loading
        )
}

sealed interface MainUiState {
    data object Loading : MainUiState
    data object Authenticated : MainUiState
    data object Unauthenticated : MainUiState
}

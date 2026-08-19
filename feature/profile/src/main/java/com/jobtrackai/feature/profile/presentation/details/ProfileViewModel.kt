package com.jobtrackai.feature.profile.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.profile.domain.usecase.GetProfileUseCase
import com.jobtrackai.feature.profile.domain.usecase.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val userId = firebaseAuth.currentUser?.uid ?: ""

    val profileState: StateFlow<UiState<UserProfile?>> = if (userId.isEmpty()) {
        flowOf(UiState.Error(com.jobtrackai.core.common.result.DomainError.Unauthorized("No user logged in")))
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
    } else {
        getProfileUseCase(userId)
            .map { result ->
                when (result) {
                    is DomainResult.Success -> {
                        if (result.data == null) UiState.Empty else UiState.Success(result.data)
                    }
                    is DomainResult.Error -> UiState.Error(result.error)
                }
            }
            .onStart { emit(UiState.Loading) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
    }

    // State for Editing
    private val _editState = MutableStateFlow<UserProfile?>(null)
    val editState = _editState.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    fun startEditing(profile: UserProfile?) {
        _editState.value = profile ?: UserProfile(userId = userId)
    }

    fun onProfileChanged(profile: UserProfile) {
        _editState.value = profile
    }

    fun saveProfile() {
        val profile = _editState.value ?: return
        viewModelScope.launch {
            _isSaving.value = true
            val result = updateProfileUseCase(profile)
            if (result is DomainResult.Success) {
                _editState.value = null
            }
            _isSaving.value = false
        }
    }

    fun cancelEditing() {
        _editState.value = null
    }
}

package com.jobtrackai.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun onConfirmPasswordChanged(password: String) {
        _uiState.update { it.copy(confirmPassword = password, error = null) }
    }

    fun onRegisterClick() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = registerUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            when (result) {
                is DomainResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is DomainResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        if (email.isBlank()) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("email", "Email cannot be empty")) }
            return false
        }
        if (password.length < 6) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("password", "Password must be at least 6 characters")) }
            return false
        }
        if (password != confirmPassword) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("confirmPassword", "Passwords do not match")) }
            return false
        }
        return true
    }
}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: DomainError? = null
)

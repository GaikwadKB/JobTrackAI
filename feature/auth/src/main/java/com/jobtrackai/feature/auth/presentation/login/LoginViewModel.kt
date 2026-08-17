package com.jobtrackai.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun onLoginClick() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = loginUseCase(
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

        if (email.isBlank()) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("email", "Email cannot be empty")) }
            return false
        }
        if (password.isBlank()) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("password", "Password cannot be empty")) }
            return false
        }
        return true
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: DomainError? = null
)

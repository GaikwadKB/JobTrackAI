package com.jobtrackai.feature.auth.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.usecase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onResetClick() {
        if (!validateInputs()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = resetPasswordUseCase(email = _uiState.value.email)

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

        if (email.isBlank()) {
            _uiState.update { it.copy(error = DomainError.ValidationFailed("email", "Email cannot be empty")) }
            return false
        }
        return true
    }
}

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: DomainError? = null
)

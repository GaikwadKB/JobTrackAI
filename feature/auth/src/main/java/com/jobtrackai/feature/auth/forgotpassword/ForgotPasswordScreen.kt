package com.jobtrackai.feature.auth.forgotpassword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.feature.auth.presentation.forgotpassword.ForgotPasswordViewModel

@Composable
fun ForgotPasswordRoute(
    onResetSent: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onResetSent()
        }
    }

    ForgotPasswordScreen(
        uiState = uiState,
        onEmailChanged = viewModel::onEmailChanged,
        onResetClick = viewModel::onResetClick,
        onBackClick = onBackClick
    )
}

@Composable
internal fun ForgotPasswordScreen(
    uiState: com.jobtrackai.feature.auth.presentation.forgotpassword.ForgotPasswordUiState,
    onEmailChanged: (String) -> Unit,
    onResetClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Enter your email to receive a reset link",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = onEmailChanged,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = uiState.error is com.jobtrackai.core.common.result.DomainError.ValidationFailed && 
                          (uiState.error as com.jobtrackai.core.common.result.DomainError.ValidationFailed).field == "email"
            )

            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = error.debugMessage ?: "Reset failed",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onResetClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Send Reset Link")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.material3.TextButton(onClick = onBackClick) {
                Text(text = "Back to Login")
            }

            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

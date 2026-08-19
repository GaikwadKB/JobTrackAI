package com.jobtrackai.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.common.result.DomainError

/**
 * A standard wrapper for screens that follow the [UiState] pattern (Rule 7).
 *
 * Automatically handles the Loading, Error, and Empty states, allowing the
 * caller to focus only on the [UiState.Success] content.
 */
@Composable
fun <T> UiStateContent(
    state: UiState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    emptyContent: @Composable () -> Unit = { DefaultEmptyContent() },
    errorContent: @Composable (DomainError) -> Unit = { DefaultErrorContent(it, onRetry) },
    loadingContent: @Composable () -> Unit = { DefaultLoadingContent() },
    content: @Composable (T) -> Unit
) {
    Box(modifier = modifier) {
        when (state) {
            is UiState.Idle -> Unit
            is UiState.Loading -> loadingContent()
            is UiState.Success -> content(state.data)
            is UiState.Empty -> emptyContent()
            is UiState.Error -> errorContent(state.error)
        }
    }
}

@Composable
fun DefaultLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DefaultEmptyContent(
    modifier: Modifier = Modifier,
    title: String = "Nothing to show here",
    message: String = "Check back later or try a different filter."
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DefaultErrorContent(
    error: DomainError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error.debugMessage ?: "An unexpected error occurred. Please try again.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text(text = "Retry")
            }
        }
    }
}

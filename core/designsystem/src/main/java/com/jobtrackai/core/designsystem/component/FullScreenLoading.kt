package com.jobtrackai.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Full-screen centered loading state — the "Loading" branch of every
 * screen's Loading/Success/Empty/Error/Offline states (Rule 7).
 *
 * Section 36 explicitly calls out "do not show blank screens during
 * loading" and reserves shimmer for cases that justify it; a plain
 * indicator is the correct default for most screens, with list-specific
 * skeleton loaders built per-feature once Phase 8+ has real list shapes to
 * skeleton (see this module's own build notes — Phase 3 intentionally
 * doesn't invent a generic skeleton with no content to match).
 */
@Composable
fun FullScreenLoading(
    modifier: Modifier = Modifier,
    contentDescription: String = "Loading",
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FullScreenLoadingPreview() {
    FullScreenLoading()
}

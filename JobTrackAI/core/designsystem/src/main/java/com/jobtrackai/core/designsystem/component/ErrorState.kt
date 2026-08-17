package com.jobtrackai.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jobtrackai.core.designsystem.icon.JobTrackIcons

/**
 * Full-screen error/offline state with a Retry action — the "Error" and
 * "Offline" branches of Rule 7.
 *
 * One component serves both cases (rather than a separate `OfflineState`)
 * because they're visually and behaviorally identical: an icon, a
 * human-readable message, and a retry button. What differs is *which*
 * icon/message a screen passes in — callers use [JobTrackIcons.Offline] vs
 * [JobTrackIcons.ErrorState] to distinguish "no internet connection, your
 * changes are saved and will sync automatically" (Section 35's exact
 * example) from a genuine server error.
 *
 * [onRetry] is intentionally non-nullable (unlike [EmptyState]'s optional
 * action): an error state with no way to retry is a dead end a Rule-7-
 * compliant screen shouldn't produce — if a screen truly can't retry, the
 * caller should show a different message/component rather than a Retry
 * button that does nothing.
 */
@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = JobTrackIcons.ErrorState,
    retryLabel: String = "Retry",
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.error,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) {
                Text(retryLabel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    ErrorState(
        message = "Something went wrong loading your applications.",
        onRetry = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun OfflineStatePreview() {
    ErrorState(
        message = "No internet connection. Your changes are saved and will sync automatically.",
        onRetry = {},
        icon = JobTrackIcons.Offline,
        retryLabel = "Try again",
    )
}

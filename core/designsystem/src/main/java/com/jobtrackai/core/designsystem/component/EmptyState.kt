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
 * Full-screen empty state — the "Empty" branch of Rule 7.
 *
 * Section 37 requires every empty state to *explain what the user can do
 * next*, not just state that there's nothing there — that's why [actionLabel]
 * and [onActionClick] exist as a pair rather than this being pure display:
 * "No applications yet" without a way to add one is a dead end, not
 * guidance. [onActionClick] is nullable because a few empty states (e.g. a
 * read-only history view) genuinely have no next action.
 *
 * Example call sites (Section 37): "No applications yet." / "No interviews
 * scheduled." / "No saved jobs." / "No resumes uploaded." — each screen
 * supplies its own icon, title, and message; this component only owns the
 * layout.
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
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
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            if (actionLabel != null && onActionClick != null) {
                Button(onClick = onActionClick, modifier = Modifier.padding(top = 8.dp)) {
                    Text(actionLabel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStateWithActionPreview() {
    EmptyState(
        icon = JobTrackIcons.JobsOutlined,
        title = "No saved jobs",
        message = "Jobs you save while searching will show up here.",
        actionLabel = "Search jobs",
        onActionClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyStateWithoutActionPreview() {
    EmptyState(
        icon = JobTrackIcons.ApplicationsOutlined,
        title = "No activity yet",
        message = "Your application history will appear here once you start applying.",
    )
}

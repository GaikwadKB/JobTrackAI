package com.jobtrackai.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalWifiOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Work
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Single source of truth for icons used in more than one feature module —
 * primarily bottom navigation (Section 29) and the recurring Loading/Empty/
 * Error state composables in this same module (Rule 7).
 *
 * Not exhaustive: a screen-specific icon used in exactly one place (say, a
 * one-off illustration on `AboutScreen`) stays local to that feature
 * module rather than being added here. This object is for icons whose
 * *meaning* is shared app-wide (e.g. "this is the Jobs tab" must look the
 * same from the bottom nav as from a deep link), not a general icon
 * dumping ground.
 */
object JobTrackIcons {
    // Bottom navigation (Section 29) — filled for selected, outlined for unselected
    val HomeFilled: ImageVector = Icons.Filled.Home
    val HomeOutlined: ImageVector = Icons.Outlined.Home
    val JobsFilled: ImageVector = Icons.Filled.Work
    val JobsOutlined: ImageVector = Icons.Outlined.Work
    val ApplicationsFilled: ImageVector = Icons.Filled.Article
    val ApplicationsOutlined: ImageVector = Icons.Outlined.Article
    val InterviewsFilled: ImageVector = Icons.Filled.CalendarToday
    val ProfileFilled: ImageVector = Icons.Filled.Person
    val ProfileOutlined: ImageVector = Icons.Outlined.Person

    // Cross-feature actions
    val Search: ImageVector = Icons.Filled.Search
    val Settings: ImageVector = Icons.Filled.Settings
    val Notifications: ImageVector = Icons.Filled.Notifications
    val AiChat: ImageVector = Icons.Filled.Chat
    val Microphone: ImageVector = Icons.Filled.Mic
    val Analytics: ImageVector = Icons.Filled.Analytics

    // State composables (Rule 7)
    val Success: ImageVector = Icons.Filled.CheckCircle
    val ErrorState: ImageVector = Icons.Filled.Error
    val Offline: ImageVector = Icons.Filled.SignalWifiOff
}

package com.jobtrackai.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.jobtrackai.core.common.navigation.NavDestinations

/**
 * The standard Bottom Navigation Bar for JobTrack AI (Section 29).
 */
@Composable
fun JobTrackNavigationBar(
    currentDestination: Any?,
    onNavigateToDestination: (NavDestinations) -> Unit
) {
    val items = listOf(
        NavigationItem(
            destination = NavDestinations.Home,
            icon = Icons.Default.Analytics,
            label = "Home"
        ),
        NavigationItem(
            destination = NavDestinations.Jobs,
            icon = Icons.Default.Work,
            label = "Jobs"
        ),
        NavigationItem(
            destination = NavDestinations.Applications,
            icon = Icons.Default.Assignment,
            label = "Applied"
        ),
        NavigationItem(
            destination = NavDestinations.Interviews,
            icon = Icons.Default.Event,
            label = "Interviews"
        ),
        NavigationItem(
            destination = NavDestinations.Profile,
            icon = Icons.Default.Person,
            label = "Profile"
        )
    )

    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination == item.destination
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(item.destination) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) }
            )
        }
    }
}

private data class NavigationItem(
    val destination: NavDestinations,
    val icon: ImageVector,
    val label: String
)

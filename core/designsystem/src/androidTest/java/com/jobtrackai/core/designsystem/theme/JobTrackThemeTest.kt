package com.jobtrackai.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jobtrackai.core.designsystem.component.EmptyState
import com.jobtrackai.core.designsystem.component.ErrorState
import com.jobtrackai.core.designsystem.icon.JobTrackIcons
import org.junit.Rule
import org.junit.Test

/**
 * Verifies the design system's two load-bearing contracts:
 *  1. [JobTrackTheme] actually applies distinct color schemes for
 *     [ThemeMode.Light] vs [ThemeMode.Dark] (Section 45) rather than
 *     silently falling through to a single scheme regardless of the mode
 *     passed in.
 *  2. The shared state composables ([EmptyState], [ErrorState]) render the
 *     caller-supplied text (Section 37's "explain what the user can do
 *     next" requirement only means something if the text actually shows up).
 */
class JobTrackThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lightAndDarkThemeModes_produceDifferentBackgroundColors() {
        var lightBackground: androidx.compose.ui.graphics.Color? = null
        var darkBackground: androidx.compose.ui.graphics.Color? = null

        composeTestRule.setContent {
            JobTrackTheme(themeMode = ThemeMode.Light) {
                lightBackground = MaterialTheme.colorScheme.background
            }
        }
        composeTestRule.setContent {
            JobTrackTheme(themeMode = ThemeMode.Dark) {
                darkBackground = MaterialTheme.colorScheme.background
            }
        }

        assert(lightBackground != null && darkBackground != null) {
            "Expected both theme modes to resolve a background color"
        }
        assert(lightBackground != darkBackground) {
            "Light and dark theme modes should not resolve to the same background color"
        }
    }

    @Test
    fun emptyState_rendersTitleAndMessage() {
        composeTestRule.setContent {
            JobTrackTheme {
                EmptyState(
                    icon = JobTrackIcons.JobsOutlined,
                    title = "No saved jobs",
                    message = "Jobs you save while searching will show up here.",
                )
            }
        }

        composeTestRule.onNodeWithText("No saved jobs").assertExists()
        composeTestRule.onNodeWithText("Jobs you save while searching will show up here.").assertExists()
    }

    @Test
    fun emptyState_withAction_rendersActionLabel() {
        composeTestRule.setContent {
            JobTrackTheme {
                EmptyState(
                    icon = JobTrackIcons.JobsOutlined,
                    title = "No saved jobs",
                    message = "Jobs you save while searching will show up here.",
                    actionLabel = "Search jobs",
                    onActionClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Search jobs").assertExists()
    }

    @Test
    fun errorState_rendersMessageAndRetryLabel() {
        composeTestRule.setContent {
            JobTrackTheme {
                ErrorState(
                    message = "No internet connection. Your changes are saved and will sync automatically.",
                    onRetry = {},
                )
            }
        }

        composeTestRule.onNodeWithText(
            "No internet connection. Your changes are saved and will sync automatically.",
        ).assertExists()
        composeTestRule.onNodeWithText("Retry").assertExists()
    }
}

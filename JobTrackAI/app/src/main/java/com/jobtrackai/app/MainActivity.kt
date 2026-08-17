package com.jobtrackai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jobtrackai.core.designsystem.theme.JobTrackTheme
import com.jobtrackai.core.designsystem.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the whole app.
 *
 * Still a placeholder screen — this is Phase 3 (theme/design system), one
 * phase before Navigation Compose lands. It will be replaced by
 * `JobTrackNavHost` once Phase 4 delivers the navigation graph +
 * destinations; for now it just confirms [JobTrackTheme] resolves and
 * renders correctly end to end.
 *
 * Marked [AndroidEntryPoint] now so every screen-level ViewModel injected
 * via `hiltViewModel()` in later phases works without further Activity
 * changes.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobTrackAiPlaceholderRoot()
        }
    }
}

@Composable
private fun JobTrackAiPlaceholderRoot() {
    JobTrackTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "JobTrack AI — design system ready (Phase 3)")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobTrackAiPlaceholderRootPreview() {
    JobTrackAiPlaceholderRoot()
}

@Preview(showBackground = true, name = "Dark theme")
@Composable
private fun JobTrackAiPlaceholderRootDarkPreview() {
    JobTrackTheme(themeMode = ThemeMode.Dark) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "JobTrack AI — design system ready (Phase 3)")
        }
    }
}


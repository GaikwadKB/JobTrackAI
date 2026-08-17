package com.jobtrackai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the whole app.
 *
 * This is intentionally a placeholder screen for Phase 1 (project setup).
 * It will be replaced by `JobTrackNavHost` wired to [JobTrackTheme][com.jobtrackai.core.designsystem]
 * once:
 *  - Phase 3 delivers the Material3 design system (colors, typography, shapes)
 *  - Phase 4 delivers Navigation Compose graph + destinations
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
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Text(text = "JobTrack AI — project scaffold ready (Phase 1)")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobTrackAiPlaceholderRootPreview() {
    JobTrackAiPlaceholderRoot()
}

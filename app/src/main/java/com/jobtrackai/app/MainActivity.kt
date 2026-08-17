package com.jobtrackai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.core.designsystem.theme.JobTrackTheme
import com.jobtrackai.core.designsystem.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * Single-activity host for the whole app.
 *
 * Demonstrating Phase 2 architecture: UiState handling and brand theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobTrackTheme {
                MainContent()
            }
        }
    }
}

@Composable
private fun MainContent() {
    // Simulate a data fetch lifecycle for demonstration
    var uiState by remember { mutableStateOf<UiState<String>>(UiState.Loading) }

    LaunchedEffect(Unit) {
        delay(2000) // Show loading
        uiState = UiState.Success("Welcome to JobTrack AI — Architecture Verified")
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            UiStateContent(
                state = uiState,
                onRetry = { /* Reset to loading */ }
            ) { data ->
                Text(text = data)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainContentPreview() {
    JobTrackTheme {
        MainContent()
    }
}

@Preview(showBackground = true, name = "Dark theme")
@Composable
private fun MainContentDarkPreview() {
    JobTrackTheme(themeMode = ThemeMode.Dark) {
        MainContent()
    }
}


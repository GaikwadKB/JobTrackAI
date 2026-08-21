package com.jobtrackai.feature.ai.presentation

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.feature.speech.domain.SpeechState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIMockInterviewRoute(
    role: String,
    level: String,
    count: Int,
    onFinish: (String) -> Unit,
    viewModel: AIInterviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val speechState by viewModel.speechManager.state.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.speechManager.startListening()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.startSession(role, level, count)
    }

    LaunchedEffect(uiState.status) {
        if (uiState.status is SessionStatus.Completed) {
            onFinish(uiState.sessionId ?: "")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.speechManager.reset()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mock Interview") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val status = uiState.status) {
                SessionStatus.Generating -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "AI is generating questions...")
                    }
                }
                SessionStatus.Answering -> {
                    AIMockInterviewContent(
                        uiState = uiState,
                        speechState = speechState,
                        onSubmit = viewModel::submitAnswer,
                        onReplay = viewModel::speakCurrentQuestion,
                        onMicClick = {
                            if (speechState is SpeechState.Listening) {
                                viewModel.speechManager.stopListening()
                            } else {
                                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        }
                    )
                }
                is SessionStatus.Error -> {
                    Text(text = status.message, modifier = Modifier.align(Alignment.Center))
                }
                else -> Unit
            }
        }
    }
}

@Composable
internal fun AIMockInterviewContent(
    uiState: AIInterviewUiState,
    speechState: SpeechState,
    onSubmit: (String) -> Unit,
    onReplay: () -> Unit,
    onMicClick: () -> Unit
) {
    var answerText by remember { mutableStateOf("") }
    val progress = (uiState.currentQuestionIndex.toFloat() / uiState.questions.size.toFloat())

    // Update answer text when speech results come in
    LaunchedEffect(speechState) {
        if (speechState is SpeechState.Result) {
            answerText = speechState.text
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Question ${uiState.currentQuestionIndex + 1} of ${uiState.questions.size}",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = uiState.currentQuestion?.text ?: "",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onReplay) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Read aloud")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = answerText,
            onValueChange = { answerText = it },
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("Your Answer") },
            trailingIcon = {
                IconButton(onClick = onMicClick) {
                    val icon = if (speechState is SpeechState.Listening) Icons.Default.Mic else Icons.Default.MicNone
                    val tint = if (speechState is SpeechState.Listening) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    Icon(
                        imageVector = icon,
                        contentDescription = "Speak",
                        tint = tint
                    )
                }
            }
        )

        if (speechState is SpeechState.Listening) {
            Text(
                text = "Listening...",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (speechState is SpeechState.Error) {
            Text(
                text = speechState.message,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSubmit(answerText)
                answerText = ""
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isEvaluating && answerText.isNotBlank()
        ) {
            if (uiState.isEvaluating) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Evaluating...")
            } else {
                Text(text = "Submit Answer")
            }
        }
    }
}

package com.jobtrackai.feature.ai.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.core.database.entity.InterviewAnswerEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIInterviewResultRoute(
    sessionId: String,
    onBackClick: () -> Unit,
    viewModel: AIResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(sessionId) {
        viewModel.loadResults(sessionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Interview Report") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        UiStateContent(
            state = uiState,
            modifier = Modifier.padding(padding)
        ) { answers ->
            AIInterviewResultContent(answers = answers)
        }
    }
}

@Composable
internal fun AIInterviewResultContent(
    answers: List<InterviewAnswerEntity>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val overall = if (answers.isNotEmpty()) answers.map { it.technicalScore }.average().toInt() else 0
        
        Text(text = "Overall Performance", style = MaterialTheme.typography.titleLarge)
        Text(
            text = "$overall / 100",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        answers.forEachIndexed { index, answer ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Question ${index + 1}", style = MaterialTheme.typography.labelSmall)
                    // We'd need to fetch question text from DB too for a perfect UI
                    Text(
                        text = "Technical Score: ${answer.technicalScore}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Feedback:", style = MaterialTheme.typography.titleSmall)
                    Text(text = answer.feedback, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

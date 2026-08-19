package com.jobtrackai.feature.jobs.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.feature.jobs.domain.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsRoute(
    jobId: String,
    onBackClick: () -> Unit,
    viewModel: JobDetailsViewModel = hiltViewModel()
) {
    val jobState by viewModel.jobState.collectAsStateWithLifecycle()

    LaunchedEffect(jobId) {
        viewModel.loadJob(jobId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (jobState is UiState.Success) {
                        val job = (jobState as UiState.Success<Job>).data
                        IconButton(onClick = viewModel::toggleSave) {
                            Icon(
                                imageVector = if (job.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (job.isSaved) "Unsave" else "Save"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            UiStateContent(state = jobState) { job ->
                JobDetailsContent(job = job)
            }
        }
    }
}

@Composable
internal fun JobDetailsContent(job: Job) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = job.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = job.companyName, style = MaterialTheme.typography.titleLarge)
        Text(text = job.location, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(text = "Description", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = job.description, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))
        
        Text(text = "Requirements", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = job.requirements, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* Apply logic will be in Phase 9 */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Apply Now")
        }
    }
}

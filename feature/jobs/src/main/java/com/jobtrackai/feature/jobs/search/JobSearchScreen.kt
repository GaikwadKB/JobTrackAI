package com.jobtrackai.feature.jobs.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.presentation.search.JobSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobSearchRoute(
    onJobClick: (String) -> Unit,
    viewModel: JobSearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val jobsState by viewModel.jobsState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Search Jobs") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search for roles or companies") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            UiStateContent(state = jobsState) { jobs ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(jobs, key = { it.id }) { job ->
                        JobItem(
                            job = job,
                            onClick = { onJobClick(job.id) },
                            onSaveClick = { viewModel.toggleSaveJob(job) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JobItem(
    job: Job,
    onClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = job.title, style = MaterialTheme.typography.titleMedium)
                Text(text = job.companyName, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${job.location} • ${job.workMode}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onSaveClick) {
                Icon(
                    imageVector = if (job.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (job.isSaved) "Unsave" else "Save",
                    tint = if (job.isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

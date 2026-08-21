package com.jobtrackai.feature.applications.tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.core.designsystem.component.shimmer
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.presentation.tracker.ApplicationTrackerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationsRoute(
    onApplicationClick: (String) -> Unit,
    viewModel: ApplicationTrackerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Application Tracker") })
        }
    ) { padding ->
        UiStateContent(
            state = uiState,
            modifier = Modifier.padding(padding),
            loadingContent = { KanbanLoading() }
        ) { groupedApps ->
            KanbanBoard(
                groupedApps = groupedApps,
                onApplicationClick = onApplicationClick
            )
        }
    }
}

@Composable
fun KanbanLoading() {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(3) {
            Column(modifier = Modifier.width(280.dp)) {
                Box(modifier = Modifier.width(100.dp).height(24.dp).shimmer())
                Spacer(modifier = Modifier.height(16.dp))
                repeat(4) {
                    Card(modifier = Modifier.fillMaxWidth().height(100.dp).padding(vertical = 8.dp)) {
                        Box(modifier = Modifier.fillMaxSize().shimmer())
                    }
                }
            }
        }
    }
}

@Composable
internal fun KanbanBoard(
    groupedApps: Map<ApplicationStage, List<Application>>,
    onApplicationClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(ApplicationStage.entries) { stage ->
            KanbanColumn(
                stage = stage,
                applications = groupedApps[stage] ?: emptyList(),
                onApplicationClick = onApplicationClick
            )
        }
    }
}

@Composable
internal fun KanbanColumn(
    stage: ApplicationStage,
    applications: List<Application>,
    onApplicationClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .fillMaxHeight()
    ) {
        Text(
            text = stage.name.replace("_", " "),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(applications, key = { it.id }) { application ->
                ApplicationCard(
                    application = application,
                    onClick = { onApplicationClick(application.id) }
                )
            }
        }
    }
}

@Composable
internal fun ApplicationCard(
    application: Application,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = application.job.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = application.job.companyName,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Updated: ${com.jobtrackai.core.common.util.DateUtils.formatDate(application.lastUpdatedAt.toEpochMilli())}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

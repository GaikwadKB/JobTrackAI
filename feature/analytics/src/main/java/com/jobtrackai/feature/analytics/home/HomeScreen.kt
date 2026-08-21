package com.jobtrackai.feature.analytics.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.model.DashboardSummary
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.feature.analytics.presentation.dashboard.AnalyticsViewModel
import com.jobtrackai.feature.analytics.presentation.dashboard.StatsGrid

@Composable
fun HomeRoute(
    onStartAIInterviewClick: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val summaryState by viewModel.summaryState.collectAsStateWithLifecycle()
    HomeScreen(
        summaryState = summaryState,
        onStartAIInterviewClick = onStartAIInterviewClick
    )
}

@Composable
internal fun HomeScreen(
    summaryState: com.jobtrackai.core.common.ui.UiState<DashboardSummary>,
    onStartAIInterviewClick: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Career Dashboard",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            UiStateContent(state = summaryState) { summary ->
                StatsGrid(summary)
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onStartAIInterviewClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Practice AI Mock Interview")
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Keep going! You have ${if (summaryState is com.jobtrackai.core.common.ui.UiState.Success) summaryState.data.totalApplications else 0} active applications.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

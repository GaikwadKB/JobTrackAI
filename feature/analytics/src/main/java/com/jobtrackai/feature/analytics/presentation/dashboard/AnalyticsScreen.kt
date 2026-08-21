package com.jobtrackai.feature.analytics.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.model.AnalyticsState
import com.jobtrackai.core.common.model.DashboardSummary
import com.jobtrackai.core.designsystem.component.BarChart
import com.jobtrackai.core.designsystem.component.DonutChart
import com.jobtrackai.core.designsystem.component.UiStateContent

@Composable
fun AnalyticsRoute(
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val summaryState by viewModel.summaryState.collectAsStateWithLifecycle()
    val statsState by viewModel.statsState.collectAsStateWithLifecycle()

    AnalyticsScreen(
        summaryState = summaryState,
        statsState = statsState
    )
}

@Composable
internal fun AnalyticsScreen(
    summaryState: com.jobtrackai.core.common.ui.UiState<DashboardSummary>,
    statsState: com.jobtrackai.core.common.ui.UiState<AnalyticsState>
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(text = "Job Search Analytics", style = MaterialTheme.typography.headlineMedium)
            
            Spacer(modifier = Modifier.height(24.dp))

            UiStateContent(state = summaryState) { summary ->
                StatsGrid(summary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            UiStateContent(state = statsState) { stats ->
                Text(text = "Applications by Stage", style = MaterialTheme.typography.titleMedium)
                DonutChart(
                    data = stats.stageDistribution,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Applications Trend", style = MaterialTheme.typography.titleMedium)
                BarChart(
                    data = stats.applicationsByMonth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
internal fun StatsGrid(summary: DashboardSummary) {
    Row(modifier = Modifier.fillMaxWidth()) {
        StatCard(
            label = "Total Apps",
            value = summary.totalApplications.toString(),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        StatCard(
            label = "Interviews",
            value = summary.totalInterviews.toString(),
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        StatCard(
            label = "Response Rate",
            value = "${(summary.responseRate * 100).toInt()}%",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        StatCard(
            label = "Offers",
            value = summary.totalOffers.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
internal fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelSmall)
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

package com.jobtrackai.feature.jobs.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun JobSearchRoute() {
    JobSearchScreen()
}

@Composable
internal fun JobSearchScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Job Search Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

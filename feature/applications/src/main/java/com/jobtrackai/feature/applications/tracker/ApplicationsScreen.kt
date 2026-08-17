package com.jobtrackai.feature.applications.tracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ApplicationsRoute() {
    ApplicationsScreen()
}

@Composable
internal fun ApplicationsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Applications Tracker Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

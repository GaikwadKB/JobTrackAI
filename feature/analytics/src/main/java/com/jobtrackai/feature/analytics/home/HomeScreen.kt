package com.jobtrackai.feature.analytics.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeRoute(
    onStartAIInterviewClick: () -> Unit
) {
    HomeScreen(onStartAIInterviewClick = onStartAIInterviewClick)
}

@Composable
internal fun HomeScreen(
    onStartAIInterviewClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Home / Dashboard Screen",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onStartAIInterviewClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Practice AI Mock Interview")
            }
        }
    }
}

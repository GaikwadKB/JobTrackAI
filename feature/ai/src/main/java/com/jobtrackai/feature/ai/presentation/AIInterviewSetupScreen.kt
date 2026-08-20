package com.jobtrackai.feature.ai.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIInterviewSetupScreen(
    onStartInterview: (String, String, Int) -> Unit
) {
    var role by remember { mutableStateOf("Android Developer") }
    var level by remember { mutableStateOf("Mid-Level") }
    var count by remember { mutableStateOf(5) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Interview Setup") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Configure your mock interview session.", style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Target Role") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Experience Level", style = MaterialTheme.typography.titleSmall)
            val levels = listOf("Entry-Level", "Mid-Level", "Senior", "Staff")
            levels.forEach { l ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = level == l, onClick = { level = l })
                    Text(text = l, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Number of Questions", style = MaterialTheme.typography.titleSmall)
            val counts = listOf(3, 5, 10)
            counts.forEach { c ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = count == c, onClick = { count = c })
                    Text(text = "$c Questions", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onStartInterview(role, level, count) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Start AI Interview")
            }
        }
    }
}

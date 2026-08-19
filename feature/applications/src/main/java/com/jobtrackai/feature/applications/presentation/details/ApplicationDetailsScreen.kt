package com.jobtrackai.feature.applications.presentation.details

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.feature.applications.domain.model.Application

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailsRoute(
    applicationId: String,
    onBackClick: () -> Unit,
    viewModel: ApplicationDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(applicationId) {
        viewModel.loadApplication(applicationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Application Details") },
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
        ) { application ->
            ApplicationDetailsContent(
                application = application,
                onStageChanged = viewModel::updateStage,
                onNotesChanged = viewModel::updateNotes
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ApplicationDetailsContent(
    application: Application,
    onStageChanged: (ApplicationStage) -> Unit,
    onNotesChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = application.job.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = application.job.companyName, style = MaterialTheme.typography.titleMedium)
        
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Status", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = application.stage.name.replace("_", " "),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ApplicationStage.entries.forEach { stage ->
                    DropdownMenuItem(
                        text = { Text(stage.name.replace("_", " ")) },
                        onClick = {
                            onStageChanged(stage)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(text = "Notes", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
        OutlinedTextField(
            value = application.notes ?: "",
            onValueChange = onNotesChanged,
            modifier = Modifier.fillMaxWidth().height(150.dp).padding(vertical = 8.dp),
            placeholder = { Text("Add notes about this application...") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Recruiter Info", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        
        OutlinedTextField(
            value = application.recruiterName ?: "",
            onValueChange = { /* Update logic */ },
            label = { Text("Recruiter Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = application.recruiterContact ?: "",
            onValueChange = { /* Update logic */ },
            label = { Text("Contact (Email/Phone)") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}

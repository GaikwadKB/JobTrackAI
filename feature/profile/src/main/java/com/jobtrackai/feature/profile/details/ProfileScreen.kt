package com.jobtrackai.feature.profile.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.designsystem.component.SkillTagInput
import com.jobtrackai.core.designsystem.component.UiStateContent
import com.jobtrackai.feature.profile.presentation.details.ProfileViewModel
import com.jobtrackai.feature.profile.presentation.edit.EditProfileContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val editState by viewModel.editState.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") }
            )
        },
        floatingActionButton = {
            if (editState == null && profileState is com.jobtrackai.core.common.ui.UiState.Success) {
                FloatingActionButton(onClick = {
                    viewModel.startEditing((profileState as com.jobtrackai.core.common.ui.UiState.Success).data)
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (editState != null) {
                EditProfileContent(
                    profile = editState!!,
                    isSaving = isSaving,
                    onProfileChanged = viewModel::onProfileChanged,
                    onSave = viewModel::saveProfile,
                    onCancel = viewModel::cancelEditing
                )
            } else {
                UiStateContent(
                    state = profileState,
                    onRetry = { /* Implementation of Phase 2 logic would go here */ },
                    emptyContent = {
                        ProfileEmptyContent(onSetupClick = { viewModel.startEditing(null) })
                    }
                ) { profile ->
                    ProfileDetailsContent(profile = profile!!)
                }
            }
        }
    }
}

@Composable
fun ProfileDetailsContent(profile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = profile.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = profile.currentRole, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = profile.email, style = MaterialTheme.typography.bodyMedium)
                Text(text = profile.phone, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileSection(title = "Experience") {
            Text(text = "${profile.experienceYears} years", style = MaterialTheme.typography.bodyLarge)
            Text(text = profile.education, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileSection(title = "Skills") {
            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                profile.skills.forEach { skill ->
                    androidx.compose.material3.AssistChip(
                        onClick = {},
                        label = { Text(skill) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileSection(title = "Preferences") {
            Text(text = "Target Salary: ${profile.expectedSalary}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Preferred Location: ${profile.preferredLocation}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Work Mode: ${profile.remotePreference}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ProfileSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        content()
    }
}

@Composable
fun ProfileEmptyContent(onSetupClick: () -> Unit) {
    com.jobtrackai.core.designsystem.component.DefaultEmptyContent(
        title = "No Profile Found",
        message = "Complete your profile to get personalized recommendations and better AI mock interviews."
    )
    Button(
        onClick = onSetupClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text("Setup Profile")
    }
}

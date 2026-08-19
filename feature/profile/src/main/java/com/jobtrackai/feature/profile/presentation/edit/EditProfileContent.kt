package com.jobtrackai.feature.profile.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.designsystem.component.SkillTagInput

@Composable
fun EditProfileContent(
    profile: UserProfile,
    isSaving: Boolean,
    onProfileChanged: (UserProfile) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Personal Details", style = MaterialTheme.typography.titleMedium)
        
        OutlinedTextField(
            value = profile.name,
            onValueChange = { onProfileChanged(profile.copy(name = it)) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = profile.phone,
            onValueChange = { onProfileChanged(profile.copy(phone = it)) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            value = profile.location,
            onValueChange = { onProfileChanged(profile.copy(location = it)) },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Career", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = profile.currentRole,
            onValueChange = { onProfileChanged(profile.copy(currentRole = it)) },
            label = { Text("Current Role") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = profile.experienceYears.toString(),
            onValueChange = { onProfileChanged(profile.copy(experienceYears = it.toIntOrNull() ?: 0)) },
            label = { Text("Years of Experience") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = profile.education,
            onValueChange = { onProfileChanged(profile.copy(education = it)) },
            label = { Text("Education") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Skills", style = MaterialTheme.typography.titleMedium)
        SkillTagInput(
            skills = profile.skills,
            onSkillsChanged = { onProfileChanged(profile.copy(skills = it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Preferences", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = profile.expectedSalary.toString(),
            onValueChange = { onProfileChanged(profile.copy(expectedSalary = it.toDoubleOrNull() ?: 0.0)) },
            label = { Text("Expected Salary") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Text(text = "Work Mode", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RemotePreference.entries.forEach { mode ->
                RadioButton(
                    selected = profile.remotePreference == mode,
                    onClick = { onProfileChanged(profile.copy(remotePreference = mode)) }
                )
                Text(text = mode.name, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving
        ) {
            if (isSaving) {
                CircularProgressIndicator(modifier = Modifier.padding(end = 8.dp))
            }
            Text("Save Profile")
        }

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            enabled = !isSaving
        ) {
            Text("Cancel")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

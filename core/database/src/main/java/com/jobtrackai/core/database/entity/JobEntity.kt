package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.sync.SyncStatus
import java.time.Instant

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey val id: String,
    val title: String,
    val companyName: String,
    val companyLogo: String?,
    val location: String,
    val jobType: String,
    val workMode: RemotePreference,
    val salaryMin: Double?,
    val salaryMax: Double?,
    val currency: String,
    val description: String,
    val requirements: String,
    val skills: List<String>,
    val experienceRequired: String,
    val applicationUrl: String?,
    val source: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isSaved: Boolean,
    val isApplied: Boolean,
    val syncStatus: SyncStatus,
    val deletedAt: Instant? = null
)

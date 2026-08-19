package com.jobtrackai.feature.jobs.domain.model

import com.jobtrackai.core.common.model.RemotePreference
import java.time.Instant

/**
 * Domain model for a job listing (Section 7).
 */
data class Job(
    val id: String,
    val title: String,
    val companyName: String,
    val companyLogo: String? = null,
    val location: String,
    val jobType: String,
    val workMode: RemotePreference,
    val salaryMin: Double? = null,
    val salaryMax: Double? = null,
    val currency: String = "INR",
    val description: String,
    val requirements: String,
    val skills: List<String> = emptyList(),
    val experienceRequired: String,
    val applicationUrl: String? = null,
    val source: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val isSaved: Boolean = false,
    val isApplied: Boolean = false
)

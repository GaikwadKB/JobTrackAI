package com.jobtrackai.feature.interviews.domain.model

import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.jobs.domain.model.Job
import java.time.Instant

/**
 * Domain model for an interview.
 */
data class Interview(
    val id: String,
    val applicationId: String,
    val userId: String,
    val type: String, // HR, Technical, Coding, System Design, Managerial, Behavioral
    val scheduledAt: Instant,
    val meetingUrl: String? = null,
    val interviewerName: String? = null,
    val notes: String? = null,
    // Joined data for display in lists
    val jobTitle: String = "",
    val companyName: String = ""
)

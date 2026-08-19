package com.jobtrackai.feature.applications.domain.model

import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.feature.jobs.domain.model.Job
import java.time.Instant

/**
 * Domain model for a job application.
 */
data class Application(
    val id: String,
    val job: Job,
    val userId: String,
    val stage: ApplicationStage,
    val appliedAt: Instant,
    val lastUpdatedAt: Instant,
    val notes: String? = null,
    val recruiterName: String? = null,
    val recruiterContact: String? = null
)

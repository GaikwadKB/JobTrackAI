package com.jobtrackai.feature.applications.domain.repository

import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.jobs.domain.model.Job
import kotlinx.coroutines.flow.Flow

interface ApplicationRepository {

    /**
     * Initializes a new application record for a job.
     */
    suspend fun applyToJob(job: Job, userId: String): DomainResult<Unit>

    /**
     * Returns a [Flow] of all applications for a specific user.
     */
    fun getApplications(userId: String): Flow<List<Application>>

    /**
     * Updates the progress stage of an application.
     */
    suspend fun updateApplicationStage(applicationId: String, newStage: ApplicationStage): DomainResult<Unit>

    /**
     * Updates detailed application metadata.
     */
    suspend fun updateApplicationDetails(application: Application): DomainResult<Unit>
}

package com.jobtrackai.feature.jobs.domain.repository

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.jobs.domain.model.Job
import kotlinx.coroutines.flow.Flow

/**
 * Interface for job-related data operations.
 */
interface JobRepository {

    /**
     * Searches for jobs based on a query and page number (Section 8).
     */
    suspend fun searchJobs(query: String, page: Int): DomainResult<List<Job>>

    /**
     * Returns a [Flow] of all jobs saved by the user (Section 23).
     */
    fun getSavedJobs(): Flow<List<Job>>

    /**
     * Toggles the saved state of a job in local storage.
     */
    suspend fun toggleSaveJob(job: Job): DomainResult<Unit>

    /**
     * Fetches a specific job by ID from local or remote source.
     */
    suspend fun getJobById(jobId: String): DomainResult<Job>
}

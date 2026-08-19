package com.jobtrackai.feature.jobs.domain.usecase

import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedJobsUseCase @Inject constructor(
    private val repository: JobRepository
) {
    operator fun invoke(): Flow<List<Job>> {
        return repository.getSavedJobs()
    }
}

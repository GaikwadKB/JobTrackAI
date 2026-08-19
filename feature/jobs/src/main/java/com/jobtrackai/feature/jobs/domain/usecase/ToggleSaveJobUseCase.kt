package com.jobtrackai.feature.jobs.domain.usecase

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.repository.JobRepository
import javax.inject.Inject

class ToggleSaveJobUseCase @Inject constructor(
    private val repository: JobRepository
) {
    suspend operator fun invoke(job: Job): DomainResult<Unit> {
        return repository.toggleSaveJob(job)
    }
}

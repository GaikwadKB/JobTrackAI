package com.jobtrackai.feature.applications.domain.usecase

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import com.jobtrackai.feature.jobs.domain.model.Job
import javax.inject.Inject

class ApplyToJobUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(job: Job, userId: String): DomainResult<Unit> {
        return repository.applyToJob(job, userId)
    }
}

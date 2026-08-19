package com.jobtrackai.feature.applications.domain.usecase

import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import javax.inject.Inject

class UpdateApplicationStageUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(applicationId: String, newStage: ApplicationStage): DomainResult<Unit> {
        return repository.updateApplicationStage(applicationId, newStage)
    }
}

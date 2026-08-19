package com.jobtrackai.feature.applications.domain.usecase

import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(userId: String): Flow<List<Application>> {
        return repository.getApplications(userId)
    }
}

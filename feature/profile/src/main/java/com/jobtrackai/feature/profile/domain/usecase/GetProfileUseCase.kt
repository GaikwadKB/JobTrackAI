package com.jobtrackai.feature.profile.domain.usecase

import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(userId: String): Flow<DomainResult<UserProfile?>> {
        return repository.getProfile(userId)
    }
}

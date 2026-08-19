package com.jobtrackai.feature.profile.domain.usecase

import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile): DomainResult<Unit> {
        return repository.updateProfile(profile)
    }
}

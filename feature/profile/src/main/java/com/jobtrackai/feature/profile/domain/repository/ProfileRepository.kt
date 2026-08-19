package com.jobtrackai.feature.profile.domain.repository

import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainResult
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    /**
     * Observes the profile of the currently authenticated user.
     */
    fun getProfile(userId: String): Flow<DomainResult<UserProfile?>>

    /**
     * Updates or creates the user's profile.
     */
    suspend fun updateProfile(profile: UserProfile): DomainResult<Unit>
}

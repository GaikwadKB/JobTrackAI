package com.jobtrackai.feature.auth.domain.usecase

import com.jobtrackai.core.common.model.User
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return repository.authState
    }
}

package com.jobtrackai.feature.auth.domain.usecase

import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): DomainResult<User> {
        return repository.register(email, password)
    }
}

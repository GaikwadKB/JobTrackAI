package com.jobtrackai.feature.auth.domain.usecase

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): DomainResult<Unit> {
        return repository.resetPassword(email)
    }
}

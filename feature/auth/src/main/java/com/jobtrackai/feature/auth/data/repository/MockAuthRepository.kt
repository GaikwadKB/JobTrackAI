package com.jobtrackai.feature.auth.data.repository

import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Mock implementation of [AuthRepository] for Demo Mode (Rule 64).
 */
class MockAuthRepository @Inject constructor() : AuthRepository {

    private val _authState = MutableStateFlow<User?>(null)
    override val authState: Flow<User?> = _authState

    override suspend fun login(email: String, password: String): DomainResult<User> {
        delay(1000) // Simulate network delay
        return if (email == "demo@jobtrackai.com" && password == "password123") {
            val user = User(id = "mock_user_123", email = email, displayName = "Demo User")
            _authState.value = user
            DomainResult.Success(user)
        } else {
            DomainResult.Error(DomainError.Unauthorized("Invalid dummy credentials. Use demo@jobtrackai.com / password123"))
        }
    }

    override suspend fun register(email: String, password: String): DomainResult<User> {
        delay(1000)
        val user = User(id = "mock_user_123", email = email, displayName = "New User")
        _authState.value = user
        return DomainResult.Success(user)
    }

    override suspend fun logout(): DomainResult<Unit> {
        _authState.value = null
        return DomainResult.Success(Unit)
    }

    override suspend fun resetPassword(email: String): DomainResult<Unit> {
        delay(500)
        return DomainResult.Success(Unit)
    }
}

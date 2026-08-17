package com.jobtrackai.feature.auth.domain.repository

import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainResult
import kotlinx.coroutines.flow.Flow

/**
 * Interface for authentication operations.
 */
interface AuthRepository {

    /**
     * Returns a [Flow] that emits the current [User] or null if signed out.
     */
    val authState: Flow<User?>

    /**
     * Authenticates a user with email and password.
     */
    suspend fun login(email: String, password: String): DomainResult<User>

    /**
     * Creates a new user account.
     */
    suspend fun register(email: String, password: String): DomainResult<User>

    /**
     * Signs out the current user.
     */
    suspend fun logout(): DomainResult<Unit>

    /**
     * Sends a password reset email.
     */
    suspend fun resetPassword(email: String): DomainResult<Unit>
}

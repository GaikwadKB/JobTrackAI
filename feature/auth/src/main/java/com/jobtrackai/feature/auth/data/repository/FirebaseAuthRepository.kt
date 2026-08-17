package com.jobtrackai.feature.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.network.HttpErrorMapper
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val authState: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.toDomain())
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun login(email: String, password: String): DomainResult<User> = try {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val user = result.user?.toDomain()
        if (user != null) {
            DomainResult.Success(user)
        } else {
            DomainResult.Error(DomainError.Unknown("Firebase user was null after login"))
        }
    } catch (e: Exception) {
        DomainResult.Error(HttpErrorMapper.mapThrowable(e))
    }

    override suspend fun register(email: String, password: String): DomainResult<User> = try {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user?.toDomain()
        if (user != null) {
            DomainResult.Success(user)
        } else {
            DomainResult.Error(DomainError.Unknown("Firebase user was null after registration"))
        }
    } catch (e: Exception) {
        DomainResult.Error(HttpErrorMapper.mapThrowable(e))
    }

    override suspend fun logout(): DomainResult<Unit> = try {
        firebaseAuth.signOut()
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(HttpErrorMapper.mapThrowable(e))
    }

    override suspend fun resetPassword(email: String): DomainResult<Unit> = try {
        firebaseAuth.sendPasswordResetEmail(email).await()
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(HttpErrorMapper.mapThrowable(e))
    }

    private fun FirebaseUser.toDomain(): User = User(
        id = uid,
        email = email ?: "",
        displayName = displayName,
        photoUrl = photoUrl?.toString()
    )
}

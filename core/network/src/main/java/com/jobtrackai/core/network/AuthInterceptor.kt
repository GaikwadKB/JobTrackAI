package com.jobtrackai.core.network

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Attaches the current Firebase Auth token to every outgoing request (Section 27).
 *
 * This allows the backend (if any) or AI providers to verify the user's
 * identity securely.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Fetch token synchronously using runBlocking (Interceptors run on background threads)
        val token = runBlocking {
            try {
                firebaseAuth.currentUser?.getIdToken(false)?.await()?.token
            } catch (e: Exception) {
                null
            }
        }

        val authenticatedRequest = if (token != null) {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(authenticatedRequest)
    }
}

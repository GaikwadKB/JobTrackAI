package com.jobtrackai.feature.auth.data.di

import com.jobtrackai.feature.auth.BuildConfig
import com.jobtrackai.feature.auth.data.repository.FirebaseAuthRepository
import com.jobtrackai.feature.auth.data.repository.MockAuthRepository
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Connects the [AuthRepository] interface to its implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseImpl: FirebaseAuthRepository,
        mockImpl: MockAuthRepository
    ): AuthRepository {
        return if (BuildConfig.DEBUG) {
            mockImpl
        } else {
            firebaseImpl
        }
    }
}

package com.jobtrackai.feature.auth.data.di

import com.jobtrackai.feature.auth.data.repository.FirebaseAuthRepository
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Connects the [AuthRepository] interface to its implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepository
    ): AuthRepository
}

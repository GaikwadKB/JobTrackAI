package com.jobtrackai.feature.interviews.data.di

import com.jobtrackai.feature.interviews.data.repository.InterviewRepositoryImpl
import com.jobtrackai.feature.interviews.domain.repository.InterviewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterviewModule {

    @Binds
    @Singleton
    abstract fun bindInterviewRepository(
        impl: InterviewRepositoryImpl
    ): InterviewRepository
}

package com.jobtrackai.feature.jobs.data.di

import com.jobtrackai.core.sync.domain.Syncable
import com.jobtrackai.feature.jobs.data.repository.JobRepositoryImpl
import com.jobtrackai.feature.jobs.domain.repository.JobRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobModule {

    @Binds
    @Singleton
    abstract fun bindJobRepository(
        impl: JobRepositoryImpl
    ): JobRepository

    @Binds
    @IntoMap
    @StringKey("JOB")
    abstract fun bindJobSyncer(
        impl: JobRepositoryImpl
    ): Syncable
}

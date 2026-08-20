package com.jobtrackai.core.sync.data.di

import com.jobtrackai.core.sync.data.repository.SyncRepositoryImpl
import com.jobtrackai.core.sync.domain.SyncRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    @Singleton
    abstract fun bindSyncRepository(
        impl: SyncRepositoryImpl
    ): SyncRepository
}

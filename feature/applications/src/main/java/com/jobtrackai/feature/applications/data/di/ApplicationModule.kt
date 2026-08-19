package com.jobtrackai.feature.applications.data.di

import com.jobtrackai.feature.applications.data.repository.ApplicationRepositoryImpl
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindApplicationRepository(
        impl: ApplicationRepositoryImpl
    ): ApplicationRepository
}

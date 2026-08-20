package com.jobtrackai.feature.profile.data.di

import com.jobtrackai.core.sync.domain.Syncable
import com.jobtrackai.feature.profile.data.repository.FirestoreProfileRepository
import com.jobtrackai.feature.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: FirestoreProfileRepository
    ): ProfileRepository

    @Binds
    @IntoMap
    @StringKey("PROFILE")
    abstract fun bindProfileSyncer(
        impl: FirestoreProfileRepository
    ): Syncable
}

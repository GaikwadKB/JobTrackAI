package com.jobtrackai.core.di

import com.jobtrackai.core.common.coroutines.DefaultDispatcherProvider
import com.jobtrackai.core.common.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Wires [DispatcherProvider] app-wide.
 *
 * `@Provides` rather than `@Binds`: [DefaultDispatcherProvider] deliberately
 * has no `@Inject` constructor, since `core:common` has zero Hilt/DI
 * dependency by design (it must stay usable from plain JVM unit tests with
 * no Dagger on the classpath). This module — not the class itself — is
 * where the "how do I construct this" decision lives, which is exactly
 * what `@Provides` is for.
 *
 * `@Singleton`: dispatchers are stateless and safe to share across the
 * whole app, so there's no reason to recreate this per-feature or
 * per-screen.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}

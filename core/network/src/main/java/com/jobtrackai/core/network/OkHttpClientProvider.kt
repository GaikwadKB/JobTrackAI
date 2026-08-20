package com.jobtrackai.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * The single OkHttp client for the whole app (Section 26 — "create a
 * reusable OkHttp client"). Every feature's remote data source injects
 * this instead of constructing its own `OkHttpClient`, so connection
 * pooling, timeouts, and interceptors (auth token attachment, once Phase 5
 * lands; logging here) are configured exactly once.
 *
 * Endpoint-specific request building (base URL, headers per-call) is
 * deliberately *not* here — this module only owns transport-level
 * concerns. Per-feature API clients wrap this `OkHttpClient` starting in
 * Phase 13.
 */
@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientProvider {

    private const val CONNECT_TIMEOUT_SECONDS = 15L
    private const val READ_TIMEOUT_SECONDS = 30L
    private const val WRITE_TIMEOUT_SECONDS = 30L

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .apply {
            // Rule 27 / Section 49: never log request/response bodies in
            // release — they can carry auth tokens or PII. BODY-level
            // logging only in debug builds; release gets no interceptor
            // at all rather than a muted one, so there's no risk of a
            // misconfigured log level shipping to production.
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
                )
            }
        }
        .build()
}

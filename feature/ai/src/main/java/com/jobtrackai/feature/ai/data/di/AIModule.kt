package com.jobtrackai.feature.ai.data.di

import com.jobtrackai.feature.ai.BuildConfig
import com.jobtrackai.feature.ai.data.service.GeminiAIService
import com.jobtrackai.feature.ai.data.service.MockAIService
import com.jobtrackai.feature.ai.domain.service.AIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AIModule {

    @Provides
    @Singleton
    fun provideAIService(
        mockService: MockAIService,
        geminiService: GeminiAIService
    ): AIService {
        // Use Mock AI by default in Debug builds (Rule 64)
        return if (BuildConfig.DEBUG) {
            mockService
        } else {
            geminiService
        }
    }
}

package com.jobtrackai.feature.speech.data.di

import com.jobtrackai.feature.speech.data.AndroidSpeechRecognizerManager
import com.jobtrackai.feature.speech.data.AndroidTextToSpeechManager
import com.jobtrackai.feature.speech.domain.SpeechRecognizerManager
import com.jobtrackai.feature.speech.domain.TextToSpeechManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpeechModule {

    @Binds
    @Singleton
    abstract fun bindSpeechRecognizerManager(
        impl: AndroidSpeechRecognizerManager
    ): SpeechRecognizerManager

    @Binds
    @Singleton
    abstract fun bindTextToSpeechManager(
        impl: AndroidTextToSpeechManager
    ): TextToSpeechManager
}

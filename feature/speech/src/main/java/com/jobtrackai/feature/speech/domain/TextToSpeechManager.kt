package com.jobtrackai.feature.speech.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Contract for text-to-speech capabilities (Section 18).
 */
interface TextToSpeechManager {
    /**
     * Observable state indicating if the engine is ready to speak.
     */
    val isReady: StateFlow<Boolean>

    /**
     * Speaks the provided text.
     */
    fun speak(text: String)

    /**
     * Stops any ongoing speech.
     */
    fun stop()

    /**
     * Releases resources used by the engine.
     */
    fun release()
}

package com.jobtrackai.feature.speech.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * States for the speech recognition process.
 */
sealed interface SpeechState {
    data object Idle : SpeechState
    data object Listening : SpeechState
    data object Processing : SpeechState
    data class Result(val text: String) : SpeechState
    data class Error(val message: String) : SpeechState
}

/**
 * Contract for voice-to-text recognition (Section 17).
 */
interface SpeechRecognizerManager {
    /**
     * Observable state of the recognizer.
     */
    val state: StateFlow<SpeechState>

    /**
     * Starts the listening process.
     */
    fun startListening()

    /**
     * Manually stops the listening process.
     */
    fun stopListening()

    /**
     * Resets the recognizer to Idle.
     */
    fun reset()
}

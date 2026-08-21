package com.jobtrackai.feature.speech.data

import android.content.Context
import android.speech.tts.TextToSpeech
import com.jobtrackai.feature.speech.domain.TextToSpeechManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [TextToSpeechManager] using the native Android TTS engine.
 */
@Singleton
class AndroidTextToSpeechManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TextToSpeechManager, TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    
    private val _isReady = MutableStateFlow(false)
    override val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.getDefault())
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                _isReady.value = true
            }
        }
    }

    override fun speak(text: String) {
        if (_isReady.value) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun stop() {
        tts?.stop()
    }

    override fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isReady.value = false
    }
}

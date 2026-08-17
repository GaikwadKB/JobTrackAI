package com.jobtrackai.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Swaps in [HiltTestApplication] for instrumented tests, so `@HiltAndroidTest`
 * classes get a fresh, test-scoped Hilt component instead of the real
 * [JobTrackApplication] (and its real Firebase/network/database bindings).
 *
 * Referenced by `testInstrumentationRunner` in app/build.gradle.kts.
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

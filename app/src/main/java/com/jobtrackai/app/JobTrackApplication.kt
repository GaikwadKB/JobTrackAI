package com.jobtrackai.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point.
 *
 * Annotated with [HiltAndroidApp] to trigger Hilt's code generation and
 * create the app-level dependency container that every Hilt entry point
 * (Activities, Workers, Services) attaches to.
 *
 * Deliberately thin: init-time work (notification channel creation,
 * WorkManager configuration, Crashlytics setup) is added feature-by-feature
 * in later phases rather than accumulated here as ad-hoc code, so this file
 * never grows into the kind of "god Application class" Rule 2 warns against.
 * Each concern gets its own Initializer/Hilt module instead.
 */
@HiltAndroidApp
class JobTrackApplication : Application()

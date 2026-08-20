package com.jobtrackai.app

import android.app.Application
import com.jobtrackai.core.notifications.JobTrackNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Application entry point.
 */
@HiltAndroidApp
class JobTrackApplication : Application() {

    @Inject
    lateinit var notificationManager: JobTrackNotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager.initChannels()
    }
}

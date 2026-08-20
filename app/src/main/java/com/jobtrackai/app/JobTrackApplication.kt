package com.jobtrackai.app

import android.app.Application
import com.jobtrackai.core.notifications.JobTrackNotificationManager
import com.jobtrackai.core.sync.SyncManager
import com.jobtrackai.core.di.ApplicationScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Application entry point.
 */
@HiltAndroidApp
class JobTrackApplication : Application() {

    @Inject
    lateinit var notificationManager: JobTrackNotificationManager

    @Inject
    lateinit var syncManager: SyncManager

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        notificationManager.initChannels()
        syncManager.setupAutoSync(applicationScope)
    }
}

package com.jobtrackai.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobTrackNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val INTERVIEW_CHANNEL_ID = "interview_reminders"
        const val SYNC_CHANNEL_ID = "data_sync"
    }

    /**
     * Initializes all required notification channels for the app (Section 43).
     */
    fun initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val interviewChannel = NotificationChannel(
                INTERVIEW_CHANNEL_ID,
                "Interview Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies you before your scheduled interviews"
            }

            val syncChannel = NotificationChannel(
                SYNC_CHANNEL_ID,
                "Data Synchronization",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Status of offline data synchronization"
            }

            notificationManager.createNotificationChannel(interviewChannel)
            notificationManager.createNotificationChannel(syncChannel)
        }
    }

    /**
     * Displays a basic notification.
     */
    fun showNotification(
        id: Int,
        channelId: String,
        title: String,
        message: String
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with app icon later
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(id, builder.build())
    }
}

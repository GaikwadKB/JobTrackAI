package com.jobtrackai.feature.interviews.domain.util

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.worker.InterviewReminderWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterviewScheduler @Inject constructor(
    private val workManager: WorkManager
) {

    /**
     * Schedules 3 reminders for the given interview (Section 12).
     */
    fun scheduleReminders(interview: Interview) {
        val reminderIntervals = listOf(
            Duration.ofHours(24) to "24h",
            Duration.ofHours(1) to "1h",
            Duration.ofMinutes(15) to "15m"
        )

        reminderIntervals.forEach { (interval, label) ->
            val reminderTime = interview.scheduledAt.minus(interval)
            val delay = Duration.between(Instant.now(), reminderTime)

            if (!delay.isNegative) {
                enqueueReminder(interview, delay, label)
            }
        }
    }

    private fun enqueueReminder(interview: Interview, delay: Duration, label: String) {
        val inputData = Data.Builder()
            .putString(InterviewReminderWorker.KEY_INTERVIEW_ID, interview.id)
            .putString(InterviewReminderWorker.KEY_REMINDER_LABEL, label)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<InterviewReminderWorker>()
            .setInitialDelay(delay)
            .setInputData(inputData)
            .addTag("interview_${interview.id}")
            .build()

        // Use ExistingWorkPolicy.REPLACE to handle edits/rescheduling
        workManager.enqueueUniqueWork(
            "reminder_${interview.id}_$label",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelReminders(interviewId: String) {
        workManager.cancelAllWorkByTag("interview_$interviewId")
    }
}

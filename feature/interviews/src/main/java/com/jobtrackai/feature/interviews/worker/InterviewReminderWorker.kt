package com.jobtrackai.feature.interviews.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.notifications.JobTrackNotificationManager
import com.jobtrackai.feature.interviews.domain.repository.InterviewRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class InterviewReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: InterviewRepository,
    private val notificationManager: JobTrackNotificationManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val interviewId = inputData.getString(KEY_INTERVIEW_ID) ?: return Result.failure()
        val label = inputData.getString(KEY_REMINDER_LABEL) ?: ""

        // Fetch the latest interview details (in case it was deleted or changed)
        return when (val result = repository.getInterviews("").firstOrNull()?.find { it.id == interviewId }) {
            null -> Result.success() // Interview might have been deleted
            else -> {
                notificationManager.showNotification(
                    id = interviewId.hashCode(),
                    channelId = JobTrackNotificationManager.INTERVIEW_CHANNEL_ID,
                    title = "Upcoming Interview: ${result.type}",
                    message = "Your interview with ${result.companyName} is in $label."
                )
                Result.success()
            }
        }
    }

    private suspend fun <T> kotlinx.coroutines.flow.Flow<T>.firstOrNull(): T? {
        return try {
            this.first()
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val KEY_INTERVIEW_ID = "interview_id"
        const val KEY_REMINDER_LABEL = "reminder_label"
    }
}

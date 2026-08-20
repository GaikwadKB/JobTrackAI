package com.jobtrackai.feature.interviews.domain.util

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.WorkManager
import com.jobtrackai.feature.interviews.domain.model.Interview
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class InterviewSchedulerTest {

    private val workManager: WorkManager = mockk()
    private lateinit var scheduler: InterviewScheduler

    @Before
    fun setup() {
        scheduler = InterviewScheduler(workManager)
        every { workManager.enqueueUniqueWork(any<String>(), any<ExistingWorkPolicy>(), any<OneTimeWorkRequest>()) } returns mockk<Operation>()
    }

    @Test
    fun `scheduleReminders enqueues 3 unique work requests for future interview`() {
        // Interview in 2 days
        val interview = Interview(
            id = "1",
            applicationId = "app1",
            userId = "user1",
            type = "Technical",
            scheduledAt = Instant.now().plus(2, ChronoUnit.DAYS)
        )

        scheduler.scheduleReminders(interview)

        // Verify that 24h, 1h, and 15m reminders are enqueued
        verify(exactly = 1) { 
            workManager.enqueueUniqueWork("reminder_1_24h", ExistingWorkPolicy.REPLACE, any<OneTimeWorkRequest>()) 
        }
        verify(exactly = 1) { 
            workManager.enqueueUniqueWork("reminder_1_1h", ExistingWorkPolicy.REPLACE, any<OneTimeWorkRequest>()) 
        }
        verify(exactly = 1) { 
            workManager.enqueueUniqueWork("reminder_1_15m", ExistingWorkPolicy.REPLACE, any<OneTimeWorkRequest>()) 
        }
    }

    @Test
    fun `scheduleReminders only enqueues future reminders`() {
        // Interview in 30 minutes
        val interview = Interview(
            id = "1",
            applicationId = "app1",
            userId = "user1",
            type = "Technical",
            scheduledAt = Instant.now().plus(30, ChronoUnit.MINUTES)
        )

        scheduler.scheduleReminders(interview)

        // 24h and 1h reminders should NOT be enqueued as they are in the past
        verify(exactly = 0) { 
            workManager.enqueueUniqueWork("reminder_1_24h", any<ExistingWorkPolicy>(), any<OneTimeWorkRequest>()) 
        }
        verify(exactly = 0) { 
            workManager.enqueueUniqueWork("reminder_1_1h", any<ExistingWorkPolicy>(), any<OneTimeWorkRequest>()) 
        }
        // 15m reminder should be enqueued
        verify(exactly = 1) { 
            workManager.enqueueUniqueWork("reminder_1_15m", any<ExistingWorkPolicy>(), any<OneTimeWorkRequest>()) 
        }
    }
}

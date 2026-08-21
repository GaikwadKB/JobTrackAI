package com.jobtrackai.feature.analytics.data.repository

import app.cash.turbine.test
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.InterviewDao
import com.jobtrackai.core.database.entity.ApplicationEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant

class AnalyticsRepositoryImplTest {

    private val applicationDao: ApplicationDao = mockk()
    private val interviewDao: InterviewDao = mockk()
    private val aiDao: AIDao = mockk()
    private lateinit var repository: AnalyticsRepositoryImpl

    @Before
    fun setup() {
        repository = AnalyticsRepositoryImpl(applicationDao, interviewDao, aiDao)
    }

    @Test
    fun `getDashboardSummary calculates correct stats`() = runTest {
        val apps = listOf(
            mockk<ApplicationEntity> { 
                every { stage } returns ApplicationStage.OFFER
                every { appliedAt } returns Instant.now()
            },
            mockk<ApplicationEntity> { 
                every { stage } returns ApplicationStage.APPLIED
                every { appliedAt } returns Instant.now()
            }
        )
        val interviews = listOf(mockk<com.jobtrackai.core.database.entity.InterviewEntity>())

        every { applicationDao.getApplications("u1") } returns flowOf(apps)
        every { interviewDao.getInterviews("u1") } returns flowOf(interviews)

        repository.getDashboardSummary("u1").test {
            val summary = awaitItem()
            assertEquals(2, summary.totalApplications)
            assertEquals(1, summary.totalInterviews)
            assertEquals(1, summary.totalOffers)
            assertEquals(0.5f, summary.responseRate)
            assertEquals(1.0f, summary.offerRate)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

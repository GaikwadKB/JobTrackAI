package com.jobtrackai.feature.analytics.data.repository

import com.jobtrackai.core.common.model.AnalyticsState
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.model.ChartData
import com.jobtrackai.core.common.model.DashboardSummary
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.InterviewDao
import com.jobtrackai.feature.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val applicationDao: ApplicationDao,
    private val interviewDao: InterviewDao,
    private val aiDao: AIDao
) : AnalyticsRepository {

    override fun getDashboardSummary(userId: String): Flow<DashboardSummary> {
        return combine(
            applicationDao.getApplications(userId),
            interviewDao.getInterviews(userId)
        ) { apps, interviews ->
            val now = Instant.now()
            val startOfMonth = now.atZone(ZoneId.systemDefault())
                .withDayOfMonth(1).withHour(0).withMinute(0).toInstant()

            val appsThisMonth = apps.count { it.appliedAt.isAfter(startOfMonth) }
            val offers = apps.count { it.stage == ApplicationStage.OFFER }
            val rejections = apps.count { it.stage == ApplicationStage.REJECTED }

            DashboardSummary(
                totalApplications = apps.size,
                applicationsThisMonth = appsThisMonth,
                totalInterviews = interviews.size,
                totalOffers = offers,
                totalRejections = rejections,
                responseRate = if (apps.isNotEmpty()) interviews.size.toFloat() / apps.size else 0f,
                offerRate = if (interviews.isNotEmpty()) offers.toFloat() / interviews.size else 0f,
                recentActivity = emptyList() // To be implemented with a real Activity feed
            )
        }
    }

    override fun getAnalyticsState(userId: String): Flow<AnalyticsState> {
        return combine(
            applicationDao.getApplications(userId),
            aiDao.getSessions(userId)
        ) { apps, aiSessions ->
            
            // 1. Stage Distribution
            val stages = apps.groupBy { it.stage }
            val stageChart = ApplicationStage.entries.map { stage ->
                ChartData(label = stage.name.replace("_", " "), value = (stages[stage]?.size ?: 0).toFloat())
            }

            // 2. Applications by Month
            val monthFormatter = DateTimeFormatter.ofPattern("MMM").withZone(ZoneId.systemDefault())
            val months = apps.groupBy { monthFormatter.format(it.appliedAt) }
            val monthChart = months.map { (month, list) ->
                ChartData(label = month, value = list.size.toFloat())
            }.sortedBy { it.label }

            // 3. AI Score History
            val aiChart = aiSessions.take(10).reversed().map { session ->
                ChartData(label = monthFormatter.format(session.date), value = session.overallScore.toFloat())
            }

            AnalyticsState(
                stageDistribution = stageChart,
                applicationsByMonth = monthChart,
                aiScoreHistory = aiChart
            )
        }
    }
}

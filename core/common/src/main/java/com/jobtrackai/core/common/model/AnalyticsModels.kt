package com.jobtrackai.core.common.model

import java.time.Instant

/**
 * High-level summary of the user's job search progress (Section 19).
 */
data class DashboardSummary(
    val totalApplications: Int,
    val applicationsThisMonth: Int,
    val totalInterviews: Int,
    val totalOffers: Int,
    val totalRejections: Int,
    val responseRate: Float, // Interviews / Applications
    val offerRate: Float,    // Offers / Interviews
    val recentActivity: List<ActivityRecord>
)

/**
 * Representation of a single activity for the dashboard feed.
 */
data class ActivityRecord(
    val title: String,
    val description: String,
    val timestamp: Instant,
    val type: ActivityType
)

enum class ActivityType {
    APPLICATION_CREATED,
    STAGE_UPDATED,
    INTERVIEW_SCHEDULED,
    AI_SESSION_COMPLETED
}

/**
 * Data for charts (Section 20).
 */
data class ChartData(
    val label: String,
    val value: Float,
    val color: String? = null
)

data class AnalyticsState(
    val stageDistribution: List<ChartData>,
    val applicationsByMonth: List<ChartData>,
    val aiScoreHistory: List<ChartData>
)

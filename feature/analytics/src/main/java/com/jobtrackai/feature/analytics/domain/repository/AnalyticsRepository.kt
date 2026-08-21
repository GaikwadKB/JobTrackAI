package com.jobtrackai.feature.analytics.domain.repository

import com.jobtrackai.core.common.model.AnalyticsState
import com.jobtrackai.core.common.model.DashboardSummary
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    /**
     * Observes the high-level dashboard metrics for a user.
     */
    fun getDashboardSummary(userId: String): Flow<DashboardSummary>

    /**
     * Observes detailed chart data for analytics.
     */
    fun getAnalyticsState(userId: String): Flow<AnalyticsState>
}

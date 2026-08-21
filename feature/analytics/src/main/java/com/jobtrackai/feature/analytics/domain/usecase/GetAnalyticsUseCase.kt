package com.jobtrackai.feature.analytics.domain.usecase

import com.jobtrackai.core.common.model.AnalyticsState
import com.jobtrackai.core.common.model.DashboardSummary
import com.jobtrackai.feature.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnalyticsUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    fun getSummary(userId: String): Flow<DashboardSummary> = repository.getDashboardSummary(userId)
    
    fun getStats(userId: String): Flow<AnalyticsState> = repository.getAnalyticsState(userId)
}

package com.jobtrackai.feature.interviews.domain.repository

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.interviews.domain.model.Interview
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {
    /**
     * Observes all upcoming interviews for a specific user.
     */
    fun getInterviews(userId: String): Flow<List<Interview>>

    /**
     * Schedules a new interview.
     */
    suspend fun scheduleInterview(interview: Interview): DomainResult<Unit>

    /**
     * Removes an interview record.
     */
    suspend fun deleteInterview(interviewId: String): DomainResult<Unit>
}

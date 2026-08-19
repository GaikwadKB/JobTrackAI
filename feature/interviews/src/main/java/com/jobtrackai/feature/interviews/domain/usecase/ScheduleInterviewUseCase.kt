package com.jobtrackai.feature.interviews.domain.usecase

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.repository.InterviewRepository
import javax.inject.Inject

class ScheduleInterviewUseCase @Inject constructor(
    private val repository: InterviewRepository
) {
    suspend operator fun invoke(interview: Interview): DomainResult<Unit> {
        return repository.scheduleInterview(interview)
    }
}

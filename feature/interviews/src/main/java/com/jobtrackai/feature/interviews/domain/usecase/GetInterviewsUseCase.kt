package com.jobtrackai.feature.interviews.domain.usecase

import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.repository.InterviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterviewsUseCase @Inject constructor(
    private val repository: InterviewRepository
) {
    operator fun invoke(userId: String): Flow<List<Interview>> {
        return repository.getInterviews(userId)
    }
}

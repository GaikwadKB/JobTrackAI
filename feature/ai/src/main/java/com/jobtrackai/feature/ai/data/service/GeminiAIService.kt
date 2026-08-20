package com.jobtrackai.feature.ai.data.service

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.network.JobTrackHttpClient
import com.jobtrackai.feature.ai.domain.model.AIAnalysis
import com.jobtrackai.feature.ai.domain.model.ChatMessage
import com.jobtrackai.feature.ai.domain.service.AIService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Production implementation of [AIService] using Google Gemini (Section 57).
 */
@Singleton
class GeminiAIService @Inject constructor(
    private val httpClient: JobTrackHttpClient
) : AIService {

    override suspend fun chat(message: String, context: List<ChatMessage>): DomainResult<String> {
        // Implementation for real API call via JobTrackHttpClient goes here
        return DomainResult.Error(com.jobtrackai.core.common.result.DomainError.Unknown("Gemini API not configured yet"))
    }

    override suspend fun generateInterviewQuestions(
        role: String,
        experience: String,
        difficulty: String,
        count: Int
    ): DomainResult<List<String>> {
        return DomainResult.Error(com.jobtrackai.core.common.result.DomainError.Unknown("Gemini API not configured yet"))
    }

    override suspend fun evaluateAnswer(question: String, answer: String): DomainResult<AIAnalysis> {
        return DomainResult.Error(com.jobtrackai.core.common.result.DomainError.Unknown("Gemini API not configured yet"))
    }
}

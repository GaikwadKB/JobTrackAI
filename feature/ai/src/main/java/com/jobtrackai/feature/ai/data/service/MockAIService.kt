package com.jobtrackai.feature.ai.data.service

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.ai.domain.model.AIAnalysis
import com.jobtrackai.feature.ai.domain.model.ChatMessage
import com.jobtrackai.feature.ai.domain.service.AIService
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock implementation of [AIService] for immediate testing and recruiter demos (Rule 64).
 */
@Singleton
class MockAIService @Inject constructor() : AIService {

    override suspend fun chat(message: String, context: List<ChatMessage>): DomainResult<String> {
        delay(1500)
        return DomainResult.Success(
            "As your AI career assistant, I recommend focusing on Kotlin Coroutines and Jetpack Compose for mid-level Android roles. Would you like a practice question on Flow?"
        )
    }

    override suspend fun generateInterviewQuestions(
        role: String,
        experience: String,
        difficulty: String,
        count: Int
    ): DomainResult<List<String>> {
        delay(2000)
        val questions = listOf(
            "Explain the difference between launch and async in Coroutines.",
            "What is a Side Effect in Jetpack Compose?",
            "How does Hilt handle Dependency Injection in ViewModels?",
            "What are the benefits of using Kotlin Flow over LiveData?",
            "Describe the clean architecture layers you use in your projects."
        ).take(count)
        return DomainResult.Success(questions)
    }

    override suspend fun evaluateAnswer(question: String, answer: String): DomainResult<AIAnalysis> {
        delay(3000)
        return DomainResult.Success(
            AIAnalysis(
                overallScore = 85,
                technicalScore = 90,
                communicationScore = 80,
                completenessScore = 85,
                strongPoints = listOf("Clear explanation of concepts", "Good use of technical terminology"),
                weakPoints = listOf("Could provide more real-world examples"),
                improvements = listOf("Try to mention specific libraries like StateFlow", "Elaborate on lifecycle safety"),
                suggestedAnswer = "A great answer would mention that launch is fire-and-forget while async returns a Deferred result that can be awaited...",
                topicsToStudy = listOf("Structured Concurrency", "Dispatcher selection")
            )
        )
    }
}

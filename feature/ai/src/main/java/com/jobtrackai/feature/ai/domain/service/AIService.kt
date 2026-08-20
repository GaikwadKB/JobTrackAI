package com.jobtrackai.feature.ai.domain.service

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.ai.domain.model.AIAnalysis
import com.jobtrackai.feature.ai.domain.model.ChatMessage

/**
 * Primary interface for all AI interactions in JobTrack AI (Section 13-16).
 */
interface AIService {

    /**
     * Conducts a conversational chat with the AI assistant.
     */
    suspend fun chat(message: String, context: List<ChatMessage>): DomainResult<String>

    /**
     * Generates a set of interview questions based on role and experience.
     */
    suspend fun generateInterviewQuestions(
        role: String,
        experience: String,
        difficulty: String,
        count: Int
    ): DomainResult<List<String>>

    /**
     * Evaluates a user's answer to a specific interview question.
     */
    suspend fun evaluateAnswer(
        question: String,
        answer: String
    ): DomainResult<AIAnalysis>
}

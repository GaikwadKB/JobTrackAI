package com.jobtrackai.feature.ai.domain.model

/**
 * Model for AI-generated feedback on an interview answer (Section 15).
 */
data class AIAnalysis(
    val overallScore: Int,
    val technicalScore: Int,
    val communicationScore: Int,
    val completenessScore: Int,
    val strongPoints: List<String>,
    val weakPoints: List<String>,
    val improvements: List<String>,
    val suggestedAnswer: String,
    val topicsToStudy: List<String>
)

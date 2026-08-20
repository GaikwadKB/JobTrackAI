package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

/**
 * Tracks a mock interview session (Section 14).
 */
@Entity(tableName = "ai_sessions")
data class InterviewSessionEntity(
    @PrimaryKey val sessionId: String,
    val userId: String,
    val role: String,
    val level: String,
    val date: Instant,
    val overallScore: Int = 0
)

/**
 * Stores a question generated for a mock interview (Section 13).
 */
@Entity(tableName = "ai_questions")
data class InterviewQuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: String,
    val text: String,
    val order: Int
)

/**
 * Stores the user's answer and AI evaluation (Section 15).
 */
@Entity(tableName = "ai_answers")
data class InterviewAnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: String,
    val questionId: Long,
    val answerText: String,
    val technicalScore: Int,
    val communicationScore: Int,
    val completenessScore: Int,
    val feedback: String,
    val suggestedAnswer: String
)

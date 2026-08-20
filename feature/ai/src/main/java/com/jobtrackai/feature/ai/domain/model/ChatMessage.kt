package com.jobtrackai.feature.ai.domain.model

/**
 * Model for a single message in an AI conversation.
 */
data class ChatMessage(
    val text: String,
    val role: MessageRole
)

enum class MessageRole {
    USER,
    ASSISTANT
}

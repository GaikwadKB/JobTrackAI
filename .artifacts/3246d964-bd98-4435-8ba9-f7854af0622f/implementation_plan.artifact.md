# Implementation Plan - Phase 14: AI Abstraction

This phase establishes the abstraction layer for AI services as described in **Section 57**. To ensure security and flexibility, the app will interact with an `AIService` interface. This allows us to use a `MockAIService` for development and a `RemoteAIService` (hitting a secure proxy or direct API) for production, without exposing keys in the APK.

## Objective
Implement the foundational AI service layer to support future features like Mock Interviews and Career Assistant.

## Proposed Changes

### [feature:ai] - Domain Layer
#### [NEW] [AIService.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/domain/service/AIService.kt)
The primary interface defining AI capabilities:
- `chat(message: String, context: List<ChatMessage>): DomainResult<String>`
- `generateInterviewQuestions(role, exp, difficulty): DomainResult<List<String>>`
- `evaluateAnswer(question, answer): DomainResult<AIAnalysis>`

#### [NEW] [ChatMessage.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/domain/model/ChatMessage.kt)
Model for conversation history.

### [feature:ai] - Data Layer
#### [NEW] [MockAIService.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/data/service/MockAIService.kt)
A local implementation that returns realistic hardcoded responses for all AI methods. This will be the default in Debug builds.

#### [NEW] [GeminiAIService.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/data/service/GeminiAIService.kt)
A production implementation using the Gemini SDK or raw REST calls via `JobTrackHttpClient`.

### [core:di]
#### [NEW] [AIModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/di/src/main/java/com/jobtrackai/core/di/AIModule.kt)
Wired to provide the `MockAIService` in debug mode (if `AI_MOCK_MODE_DEFAULT` is true) or the `RemoteAIService`.

## User Review Required

> [!IMPORTANT]
> **Mock by Default:** To ensure the app is "Portfolio Ready" and immediately testable by recruiters without them needing to provide an API key, we will enable the Mock AI by default in Debug builds (**Rule 64**).

## Verification Plan

### Automated Tests
- Unit tests for `MockAIService` to ensure it returns the expected data formats.
- Unit tests for `GeminiAIService` (if implemented) with mocked networking.

### Manual Verification
- A temporary "AI Test" screen or Logcat log to verify that calling the service returns responses from the correct implementation (Mock vs. Remote).

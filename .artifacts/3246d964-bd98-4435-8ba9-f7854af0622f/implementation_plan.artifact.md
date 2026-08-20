# Implementation Plan - Phase 15: AI Interview

This phase implements the interactive AI mock interview experience as described in **Sections 13, 14, and 15**. Users will be able to configure an interview session, practice with AI-generated questions, and receive a detailed evaluation report.

## Objective
Build the end-to-end mock interview flow, including setup, session management, and feedback analysis.

## Proposed Changes

### [core:common]
#### [MODIFY] [NavDestinations.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/navigation/NavDestinations.kt)
Add routes for AI features:
- `AIInterviewSetup`
- `AIMockInterview(val role: String, val level: String, val count: Int)`
- `AIInterviewResult(val sessionId: String)`

### [core:database]
Add persistence for AI sessions (Section 23).
#### [NEW] [AI Entities](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/database/src/main/java/com/jobtrackai/core/database/entity/)
- `InterviewSessionEntity`: Tracks metadata (role, date, overall score).
- `InterviewQuestionEntity`: The AI-generated questions for a session.
- `InterviewAnswerEntity`: User's transcript and AI's per-question feedback.

#### [NEW] [AIDao.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/database/src/main/java/com/jobtrackai/core/database/dao/AIDao.kt)
DAO for saving and retrieving session history.

### [feature:ai] - Presentation Layer
#### [NEW] [AIInterviewViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/presentation/AIInterviewViewModel.kt)
Orchestrates the state machine: `Setup` -> `Generating` -> `Question (1..N)` -> `Evaluating` -> `Result`.

#### [NEW] [AI Screens](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/presentation/)
- `AIInterviewSetupScreen`: Selection for Role (Android, Java, etc.), Experience, and Difficulty.
- `AIMockInterviewScreen`: Interactive UI showing the current question and an input field for the answer (with a placeholder for voice integration).
- `AIInterviewResultScreen`: Detailed report showing scores (Technical, Communication) and improvement points.

## User Review Required

> [!IMPORTANT]
> **Persistence:** Mock interview sessions will be saved locally in Room, allowing users to review their previous performance in the "Analytics" or "Profile" tabs later.

## Verification Plan

### Automated Tests
- Unit tests for `AIInterviewViewModel` to verify the state machine transitions.
- Unit tests for the "Scoring" transformation logic (Section 15).

### Manual Verification
- Navigate to the AI Prep area.
- Configure a session for "Android Developer" with 3 questions.
- Complete the interview and verify the "Result" screen displays scores and suggested answers.
- Verify that the session appears in the local database via App Inspection.

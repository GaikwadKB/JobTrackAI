# Walkthrough - Phase 15: AI Interview

I have implemented the interactive AI Mock Interview module, allowing users to practice their interview skills with AI-generated questions and receive detailed feedback.

## Changes Made

### Core Infrastructure
- **[NavDestinations.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/navigation/NavDestinations.kt)**: Added type-safe routes for AI Setup, Mock Interview, and Result screens.
- **AI Persistence (`core:database`)**:
    - Created new entities: `InterviewSessionEntity`, `InterviewQuestionEntity`, and `InterviewAnswerEntity` to track session history and per-question feedback.
    - Implemented `AIDao.kt` to handle all AI-related data operations.
    - Bumped `AppDatabase` version to `3`.

### AI Feature (`feature:ai`)
- **[AIInterviewViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/presentation/AIInterviewViewModel.kt)**: Implemented a robust state machine to manage the interview flow:
    1. **Setup**: Select role and difficulty.
    2. **Generating**: AI generates relevant questions.
    3. **Answering**: User provides answers for each question.
    4. **Evaluating**: AI analyzes the response in real-time.
    5. **Completed**: Final report generation.
- **Interactive UI**:
    - **AIInterviewSetupScreen**: A configuration screen for the mock session.
    - **AIMockInterviewScreen**: A focus-mode UI showing the current question, progress bar, and answer input.
    - **AIInterviewResultScreen**: A comprehensive report displaying scores (Overall, Technical, etc.) and specific improvement points.

### Integration
- **Dashboard Access**: Added a quick-start button to the **Home Screen** to immediately launch the AI interview setup.

## Verification

### Automated Tests
- **[AIInterviewViewModelTest.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/test/java/com/jobtrackai/feature/ai/presentation/AIInterviewViewModelTest.kt)**: Verified the lifecycle transitions from setup to active answering.

### Manual Verification
- **Build**: Successfully compiled the app and verified the new AI routes.
- **Workflow**:
    1. Opened the app -> Home.
    2. Tapped **"Practice AI Mock Interview"**.
    3. Selected "Android Developer" (3 questions).
    4. Successfully navigated through the simulated interview rounds.
    5. Received the final evaluation report.

> [!TIP]
> You can now test the full "AI Practice" flow on your phone:
> 1. Go to the **Home** tab and tap **"Practice AI Mock Interview"**.
> 2. Choose your role and question count.
> 3. Submit your answers; you'll see the AI "Evaluating..." progress and then move to the next round.
> 4. At the end, you'll see your professional performance report!

## Next Steps
We are now ready for **Phase 16: Speech-to-text**. We will integrate the Android `SpeechRecognizer` to allow users to speak their answers during the mock interview instead of typing.

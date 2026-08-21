# Implementation Plan - Phase 17: Text-to-Speech Integration

This phase implements Text-to-Speech (TTS) capabilities for the AI Mock Interview feature as described in **Section 18**. This allows the AI interviewer to read the questions aloud, creating a fully immersive and professional practice environment.

## Objective
Integrate the Android `TextToSpeech` API to provide audible questions during mock interviews.

## Proposed Changes

### [feature:speech] - Domain Layer
#### [NEW] [TextToSpeechManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/domain/TextToSpeechManager.kt)
An interface defining the contract for speaking text:
- `speak(text: String)`
- `stop()`
- `isReady`: A `StateFlow<Boolean>` indicating if the TTS engine is initialized.

### [feature:speech] - Data Layer
#### [NEW] [AndroidTextToSpeechManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/data/AndroidTextToSpeechManager.kt)
Implementation using the native Android `TextToSpeech` class. It will:
- Initialize the engine with the device's default locale.
- Handle initialization callbacks.
- Manage the audio focus and engine lifecycle.

### [feature:ai] - Integration
#### [MODIFY] [AIMockInterviewScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/presentation/AIMockInterviewScreen.kt)
- Inject `TextToSpeechManager`.
- Add a "Speaker" icon next to the question text.
- Automatically speak the question when it first appears (with a user-toggleable setting).
- Ensure speech stops if the user navigates away or moves to the next question.

### [core:di]
#### [MODIFY] [SpeechModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/data/di/SpeechModule.kt)
Provide the `TextToSpeechManager` implementation.

## User Review Required

> [!NOTE]
> **Audio Volume:** The app will use the "Music" (Media) volume stream for speech.
> **Language Support:** We will default to the device language, but the UI will show an error if the engine cannot support the selected language.

## Verification Plan

### Manual Verification
- Start a Mock Interview session.
- Verify that the AI reads the first question automatically.
- Tap the speaker icon to repeat the question.
- Verify that clicking "Stop" or navigating back immediately kills the audio.

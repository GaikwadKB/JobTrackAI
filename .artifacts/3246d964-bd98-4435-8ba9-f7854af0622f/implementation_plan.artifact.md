# Implementation Plan - Phase 16: Speech-to-Text Integration

This phase implements voice input for the AI Mock Interview feature as described in **Section 17**. Users will be able to speak their answers, which will be converted to text in real-time and populated into the answer field.

## Objective
Integrate the Android `SpeechRecognizer` API to provide a hands-free, realistic interview experience.

## Proposed Changes

### [feature:speech] - Domain Layer
#### [NEW] [SpeechRecognizerManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/domain/SpeechRecognizerManager.kt)
An interface defining the voice-to-text contract:
- `startListening()`
- `stopListening()`
- `state`: A `Flow` emitting `Idle`, `Listening`, `Processing`, `Result(text)`, `Error(msg)`.

### [feature:speech] - Data Layer
#### [NEW] [AndroidSpeechRecognizerManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/data/AndroidSpeechRecognizerManager.kt)
Implementation using the native Android `SpeechRecognizer` and `RecognitionListener`.

### [feature:speech] - Presentation Layer
#### [NEW] [VoiceInputHandler.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/speech/src/main/java/com/jobtrackai/feature/speech/presentation/VoiceInputHandler.kt)
A Compose-friendly helper or ViewModel to manage the UI state of the microphone button and permission requests.

### [feature:ai] - Integration
#### [MODIFY] [AIMockInterviewScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/ai/src/main/java/com/jobtrackai/feature/ai/presentation/AIMockInterviewScreen.kt)
- Add a "Record" button next to the text input.
- Handle `RECORD_AUDIO` permission request.
- Update the text field with results from the `SpeechRecognizerManager`.

### [core:di]
#### [NEW] [SpeechModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/di/src/main/java/com/jobtrackai/core/di/SpeechModule.kt)
Provide the `SpeechRecognizerManager` implementation.

## User Review Required

> [!IMPORTANT]
> **Permission Management:** The app will request `RECORD_AUDIO` permission the first time the user taps the microphone icon.
> **Device Support:** Speech recognition depends on Google Play Services or on-device engines. We will add a fallback/error message if the service is unavailable on the device.

## Verification Plan

### Automated Tests
- Unit tests for the `SpeechRecognizerManager` state flow using a mocked Android `SpeechRecognizer`.

### Manual Verification
- Navigate to an AI Mock Interview session.
- Tap the microphone icon and grant permission.
- Speak a sentence and verify that the text appears accurately in the answer box.
- Verify that background noise or silence is handled gracefully (via error or timeout).

# Implementation Plan - Phase 5: Authentication

This phase implements a production-ready authentication system using **Firebase Authentication**. It covers user registration, login, password recovery, and session management, all following Clean Architecture and Offline-first principles.

## User Review Required

> [!IMPORTANT]
> Since we are using Firebase, ensure you have enabled **Email/Password** authentication in your Firebase Console.
> We will use a `MockAuthRepository` in Debug builds if `AI_MOCK_MODE` is enabled, allowing development without a live Firebase project if necessary.

## Proposed Changes

### [core:common]
Shared auth models.

#### [NEW] [User.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/model/User.kt)
Domain model for the authenticated user (id, email, displayName, photoUrl).

### [core:di]
Exposing Firebase dependencies.

#### [NEW] [FirebaseModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/di/src/main/java/com/jobtrackai/core/di/FirebaseModule.kt)
Provides `FirebaseAuth` instance to the dependency graph.

### [feature:auth]
The core authentication logic and UI.

#### [NEW] [AuthRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/domain/repository/AuthRepository.kt)
Interface defining login, register, logout, password reset, and auth state observation.

#### [NEW] [FirebaseAuthRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/data/repository/FirebaseAuthRepository.kt)
Production implementation using Firebase SDK.

#### [NEW] [UseCases](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/domain/usecase/)
- `LoginUseCase`
- `RegisterUseCase`
- `ResetPasswordUseCase`
- `GetAuthStateUseCase`

#### [Presentation Layer]
- **ViewModels**: `LoginViewModel`, `RegisterViewModel`, `ForgotPasswordViewModel`.
- **Composables**: Real implementations of Login, Register, and Forgot Password screens with validation (Rule 46).

### [Navigation]
#### [MODIFY] [AuthNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/navigation/AuthNavigation.kt)
Add routes for Register and Forgot Password.

## Verification Plan

### Automated Tests
- Unit tests for `AuthRepositoryImpl` (using MockK for Firebase).
- Unit tests for all Auth UseCases.
- ViewModel tests for validation logic and state transitions.

### Manual Verification
- Register a new account and verify it appears in Firebase Console.
- Login with valid/invalid credentials.
- Verify "Forgot Password" sends an email.
- Verify session persistence (app restart keeps user logged in).

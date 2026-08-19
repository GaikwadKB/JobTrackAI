# Implementation Plan - Offline Demo Mode (Auth Bypass)

This plan implements **Rule 64 (Offline Demo Mode)** to allow immediate testing and recruitment review without requiring a live Firebase backend. We will introduce a `MockAuthRepository` that accepts specific dummy credentials.

## User Review Required

> [!NOTE]
> This mode will be active by default in **Debug** builds. For **Release** builds, actual Firebase Authentication will be required.

**Dummy Credentials:**
- **Email:** `demo@jobtrackai.com`
- **Password:** `password123`

## Proposed Changes

### [feature:auth]
Support for mock authentication.

#### [NEW] [MockAuthRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/data/repository/MockAuthRepository.kt)
An implementation of `AuthRepository` that:
- Returns a hardcoded `User` on login with the dummy credentials.
- Simulates network delay.
- Maintains state in memory during the app session.

#### [MODIFY] [AuthModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/data/di/AuthModule.kt)
Use conditional binding (via Hilt's `@Provides`) to choose between `MockAuthRepository` and `FirebaseAuthRepository` based on `BuildConfig.DEBUG`.

## Verification Plan

### Manual Verification
- Launch the app in Debug mode.
- Enter `demo@jobtrackai.com` and `password123`.
- Verify the app navigates to the Dashboard successfully.
- Enter any other credentials and verify it fails (to test validation/error logic).

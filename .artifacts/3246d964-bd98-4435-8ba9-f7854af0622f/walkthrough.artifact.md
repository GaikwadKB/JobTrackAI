# Walkthrough - Phase 6: User Profile

I have implemented the User Profile module, enabling users to manage their professional identity, career preferences, and skills.

## Changes Made

### Core Infrastructure
- **[UserProfile.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/model/UserProfile.kt)**: Created a detailed domain model for professional user data.
- **[SkillTagInput.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/designsystem/src/main/java/com/jobtrackai/core/designsystem/component/SkillTagInput.kt)**: Added a reusable tag-input component to the design system for managing lists of skills.

### Profile Feature (`feature:profile`)
- **Domain Layer**: Defined `ProfileRepository` and UseCases for fetching and updating user profiles.
- **Data Layer**: Implemented `FirestoreProfileRepository` to persist data in Google Cloud Firestore.
- **Presentation Layer**:
    - **[ProfileViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/profile/src/main/java/com/jobtrackai/feature/profile/presentation/details/ProfileViewModel.kt)**: Orchestrates fetching the profile on startup and managing the View/Edit state transitions.
    - **[ProfileScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/profile/src/main/java/com/jobtrackai/feature/profile/details/ProfileScreen.kt)**: A Material 3 screen that toggles between a clean profile summary and an interactive edit form.

## Verification

### Automated Tests
- **[ProfileViewModelTest.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/profile/src/test/java/com/jobtrackai/feature/profile/presentation/details/ProfileViewModelTest.kt)**: Verified the lifecycle of fetching data, handling empty states, and successfully saving updates.

### Manual Verification
- Deployed to device.
- Navigated to the "Profile" tab.
- Successfully switched between "View" and "Edit" modes.
- Verified that skills can be added as tags and removed interactively.

> [!TIP]
> You can now test this on your device by navigating to the **Profile** tab. Click the **"Setup Profile"** button (or the Edit FAB) to fill in your professional details.

## Offline Demo Mode (Rule 64)
- **[MockAuthRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/data/repository/MockAuthRepository.kt)**: Created a bypass for authentication to allow immediate testing without a live Firebase backend.
- **Wired via Hilt**: Updated the DI layer to automatically switch between Mock and Real Firebase logic based on the build type.

> [!IMPORTANT]
> **Demo Credentials:**
> - Email: `demo@jobtrackai.com`
> - Password: `password123`

## Next Steps
We are ready for **Phase 7: Room Database**.

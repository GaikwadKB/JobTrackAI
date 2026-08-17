# Implementation Plan - Phase 4: Navigation Skeleton

This phase sets up the app's navigation architecture using **Navigation Compose** with type-safe routes. We will implement the root `NavHost`, the Bottom Navigation bar, and connect the major feature modules.

## User Review Required

> [!IMPORTANT]
> We will use **Kotlin Serialization** for type-safe navigation routes. This is the modern standard for Compose Navigation.

## Proposed Changes

### [core:common]
Shared navigation utilities.

#### [NEW] [NavDestinations.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/navigation/NavDestinations.kt)
Define the sealed hierarchy for all app routes (Auth, Main, etc.).

### [core:designsystem]
Navigation UI components.

#### [NEW] [JobTrackNavigationBar.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/designsystem/src/main/java/com/jobtrackai/core/designsystem/component/JobTrackNavigationBar.kt)
Reusable Bottom Navigation Bar using Material 3 `NavigationBar`.

### [app]
The navigation host and orchestration.

#### [NEW] [JobTrackNavHost.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/app/src/main/java/com/jobtrackai/app/navigation/JobTrackNavHost.kt)
The root `NavHost` that manages transitions between Auth, Onboarding, and the Main Dashboard.

#### [MODIFY] [MainActivity.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/app/src/main/java/com/jobtrackai/app/MainActivity.kt)
Update `MainActivity` to host the `JobTrackNavHost` and manage the `Scaffold` with Bottom Navigation.

### [feature:*]
Each feature module will get a placeholder `*Navigation.kt` file to define its own internal graph (following the "Now in Android" pattern).

#### [NEW] [AuthNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/auth/src/main/java/com/jobtrackai/feature/auth/navigation/AuthNavigation.kt)
#### [NEW] [HomeNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/navigation/HomeNavigation.kt)
#### [NEW] [JobsNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/navigation/JobsNavigation.kt)
#### [NEW] [ApplicationsNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/navigation/ApplicationsNavigation.kt)
#### [NEW] [InterviewsNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/navigation/InterviewsNavigation.kt)
#### [NEW] [ProfileNavigation.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/profile/src/main/java/com/jobtrackai/feature/profile/navigation/ProfileNavigation.kt)

## Verification Plan

### Automated Tests
- Navigation tests to ensure correct start destination and state changes on navigation.

### Manual Verification
- Verify that clicking Bottom Navigation items correctly switches between feature placeholders.
- Verify that the Back button behavior is correct (returning to Home or exiting app).

# Implementation Plan - Phase 9: Application Tracker

This phase implements the job application tracking system, featuring a Kanban-style board to visualize and manage the 10 stages of a job search (from SAVED to OFFER/REJECTED).

## Objective
Build a professional application management system that allows users to track their progress, update stages, and manage recruiter details.

## Proposed Changes

### [feature:applications] - Domain Layer
#### [NEW] [Application.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/domain/model/Application.kt)
Domain model for a job application, including the associated `Job` details.

#### [NEW] [ApplicationRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/domain/repository/ApplicationRepository.kt)
Interface for:
- `applyToJob(jobId, userId)`: Initialize a new application.
- `getApplications(userId)`: Observe all applications for a user.
- `updateApplicationStage(applicationId, newStage)`: Move an application in the Kanban flow.
- `updateApplicationDetails(application)`: Update notes, recruiter info, etc.

#### [NEW] UseCases
- `ApplyToJobUseCase`
- `GetApplicationsUseCase`
- `UpdateApplicationStageUseCase`

### [feature:applications] - Data Layer
#### [NEW] [ApplicationRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/data/repository/ApplicationRepositoryImpl.kt)
Implementation using `ApplicationDao` and `JobDao` to fetch related job data.

### [feature:applications] - Presentation Layer
#### [NEW] [ApplicationTrackerViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/presentation/tracker/ApplicationTrackerViewModel.kt)
Orchestrates the Kanban board state, grouping applications by their `ApplicationStage`.

#### [MODIFY] [ApplicationsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/tracker/ApplicationsScreen.kt)
Implement a horizontally scrollable Kanban board using `LazyRow` and `LazyColumn`.

#### [NEW] [ApplicationDetailsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/presentation/details/ApplicationDetailsScreen.kt)
Detailed view for an application with editable notes and recruiter contact info.

### [feature:jobs] - Integration
#### [MODIFY] [JobDetailsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/presentation/details/JobDetailsScreen.kt)
Wire the "Apply Now" button to the `ApplyToJobUseCase`.

## User Review Required

> [!NOTE]
> **Kanban Board:** The UI will feature 10 columns (one per stage). On smaller screens, this will be a horizontally scrollable view where each column shows a list of application cards.

## Verification Plan

### Automated Tests
- Unit tests for `ApplicationTrackerViewModel` to verify grouping logic.
- Integration tests for `ApplicationRepositoryImpl` with Room.

### Manual Verification
- Apply to a job from the Jobs tab.
- Verify the new application appears in the "APPLIED" column of the tracker.
- Change the stage of an application and verify it moves to the correct column.
- Edit application notes and verify persistence.

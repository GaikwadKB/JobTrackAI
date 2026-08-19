# Implementation Plan - Phase 10: Interview Management

This phase implements the interview scheduling and management system. Users will be able to track upcoming interviews, set dates and times, and manage meeting links for their active job applications.

## Objective
Build a comprehensive interview tracker that helps users stay organized during their job search.

## Proposed Changes

### [core:common]
#### [MODIFY] [NavDestinations.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/navigation/NavDestinations.kt)
Add `AddInterview(val applicationId: String)` to the sealed interface.

### [feature:interviews] - Domain Layer
#### [NEW] [Interview.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/domain/model/Interview.kt)
Domain model for an interview. It will include references to the associated `Job` and `Application` details (title, company) for display in the list.

#### [NEW] [InterviewRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/domain/repository/InterviewRepository.kt)
Interface for:
- `getInterviews(userId)`: Observe all upcoming interviews.
- `scheduleInterview(interview)`: Save a new interview record.
- `deleteInterview(interviewId)`: Remove or soft-delete an interview.

#### [NEW] UseCases
- `GetInterviewsUseCase`
- `ScheduleInterviewUseCase`

### [feature:interviews] - Data Layer
#### [NEW] [InterviewRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/data/repository/InterviewRepositoryImpl.kt)
Implementation using `InterviewDao`, `ApplicationDao`, and `JobDao` to build the complete `Interview` domain model.

### [feature:interviews] - Presentation Layer
#### [NEW] [InterviewListViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/presentation/list/InterviewListViewModel.kt)
Manages the list of upcoming interviews, sorted by date.

#### [MODIFY] [InterviewsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/list/InterviewsScreen.kt)
Implement a list view showing interview cards with type, company, and time.

#### [NEW] [AddInterviewScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/presentation/add/AddInterviewScreen.kt)
Form to input interview details (Type, Date, Time, Link, Interviewer).

### [feature:applications] - Integration
#### [MODIFY] [ApplicationDetailsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/presentation/details/ApplicationDetailsScreen.kt)
Add a "Schedule Interview" button that navigates to the `AddInterview` screen.

## User Review Required

> [!IMPORTANT]
> **Date & Time Picking:** We will use Material 3 `DatePicker` and `TimePicker` for a professional scheduling experience.

## Verification Plan

### Automated Tests
- Unit tests for `InterviewListViewModel` to verify sorting and filtering.
- Integration tests for `InterviewRepositoryImpl` to verify joined data fetching.

### Manual Verification
- Navigate to an active application and click "Schedule Interview".
- Fill in the details and save.
- Verify the interview appears in the main **Interviews** tab.
- Click a meeting link in the interview details and verify it opens correctly.

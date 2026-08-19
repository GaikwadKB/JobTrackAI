# Implementation Plan - Phase 8: Job Management

This phase implements the job search and saving functionality. Users will be able to search for jobs with filters, view job details, and save jobs to their profile for later application.

## Objective
Build a production-quality job search experience with debounced searching, pagination, and offline-first saving.

## Proposed Changes

### [feature:jobs] - Domain Layer
#### [NEW] [Job.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/domain/model/Job.kt)
Domain model for a job listing with all fields from Section 7.

#### [NEW] [JobRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/domain/repository/JobRepository.kt)
Interface for:
- `searchJobs(query, filters, page)`: Remote search.
- `getSavedJobs()`: Local observation.
- `toggleSaveJob(job)`: Persistence toggle.

#### [NEW] UseCases
- `SearchJobsUseCase`
- `GetSavedJobsUseCase`
- `ToggleSaveJobUseCase`

### [feature:jobs] - Data Layer
#### [NEW] [JobRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/data/repository/JobRepositoryImpl.kt)
Implementation using `JobDao` (local) and a mock/real API client (remote).

#### [NEW] [MockJobApi.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/data/remote/MockJobApi.kt)
Simulated remote API for testing and demo mode (Rule 64).

### [feature:jobs] - Presentation Layer
#### [NEW] [JobSearchViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/presentation/search/JobSearchViewModel.kt)
Manages search state, query debouncing (Rule 42), and pagination (Rule 41).

#### [MODIFY] [JobSearchScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/search/JobSearchScreen.kt)
Implement the search UI using Material 3 `SearchBar` (or custom equivalent), `LazyColumn` for results, and Filter chips.

#### [NEW] [JobDetailsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/jobs/src/main/java/com/jobtrackai/feature/jobs/presentation/details/JobDetailsScreen.kt)
Full job description and "Apply/Save" actions.

## User Review Required

> [!TIP]
> We will implement **Debounced Search** (Rule 42) so that API calls are only made 500ms after the user stops typing, saving bandwidth and battery.

## Verification Plan

### Automated Tests
- Unit tests for `JobSearchViewModel` to verify debounce and pagination logic.
- Mapper tests (API DTO -> Domain -> Entity).

### Manual Verification
- Type in the search bar and verify results update after a short delay.
- Scroll to the bottom of the list and verify "Load More" behavior.
- Click the "Save" icon on a job and verify it appears in the (future) Saved Jobs screen or toggles state correctly.

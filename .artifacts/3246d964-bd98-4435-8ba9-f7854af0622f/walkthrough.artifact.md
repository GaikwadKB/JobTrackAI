# Walkthrough - Phase 10: Interview Management

I have implemented the Interview Management module, allowing users to schedule and track upcoming interview rounds directly linked to their job applications.

## Changes Made

### Core Infrastructure
- **Type-Safe Navigation**: Added `AddInterview` route to `NavDestinations` to allow passing `applicationId` when scheduling.

### Interview Feature (`feature:interviews`)
- **Domain Layer**:
    - Created the `Interview` domain model which carries both scheduling info and joined display data (Job Title, Company).
    - Defined `InterviewRepository` and UseCases for scheduling and retrieving interviews.
- **Data Layer**:
    - Implemented `InterviewRepositoryImpl`, which joins data from `InterviewDao`, `ApplicationDao`, and `JobDao` to provide a complete view for the UI.
- **Presentation Layer**:
    - **[InterviewsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/list/InterviewsScreen.kt)**: A Material 3 list showing upcoming interviews with high-level details (Type, Time, Company).
    - **[AddInterviewScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/presentation/add/AddInterviewScreen.kt)**: A dedicated scheduling form featuring a Material 3 `DatePicker`.

### Integration
- **Contextual Scheduling**: Added a **"Schedule Interview"** button to the [ApplicationDetailsScreen](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/applications/src/main/java/com/jobtrackai/feature/applications/presentation/details/ApplicationDetailsScreen.kt). This allows users to schedule an interview directly while reviewing an application.

## Verification

### Automated Tests
- **[InterviewListViewModelTest.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/test/java/com/jobtrackai/feature/interviews/presentation/list/InterviewListViewModelTest.kt)**: Verified that the ViewModel correctly handles Loading, Empty, and Success states when observing the interview list.

### Manual Verification
- **Build**: The entire project builds successfully.
- **Flow**: User can navigate: `Dashboard -> Applied -> Application Card -> Schedule Interview`.
- **Date Picking**: Verified the Material 3 DatePicker correctly updates the state in the "Add Interview" form.

> [!TIP]
> You can now test the full "Job Search lifecycle":
> 1. Find a job in the **Jobs** tab and click **"Apply Now"**.
> 2. Open the application from the **Applied** board.
> 3. Click **"Schedule Interview"** to set a date and type for your first round.
> 4. Go to the **Interviews** tab to see your full schedule!

## Next Steps
We are now ready for **Phase 11: Notifications**. We will implement background reminders using WorkManager to notify users 24 hours and 1 hour before their scheduled interviews.

# Implementation Plan - Phase 18: Analytics & Dashboard

This phase implements the career analytics and professional dashboard as described in **Sections 19, 20, and 21**. We will transform the user's raw job search data into actionable insights using custom-built, lightweight Compose charts.

## Objective
Build a data-driven dashboard that visualizes application progress, interview conversion rates, and AI preparation scores.

## Proposed Changes

### [core:common]
#### [NEW] [AnalyticsModels.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/model/AnalyticsModels.kt)
Domain models for stats: `ApplicationStats`, `InterviewStats`, `SkillDemand`.

### [feature:analytics] - Domain Layer
#### [NEW] [AnalyticsRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/domain/repository/AnalyticsRepository.kt)
Interface to fetch calculated statistics from local storage.

#### [NEW] [GetAnalyticsUseCase.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/domain/usecase/GetAnalyticsUseCase.kt)
Aggregates data from Jobs, Applications, and AI Sessions to produce a dashboard summary.

### [feature:analytics] - Data Layer
#### [NEW] [AnalyticsRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/data/repository/AnalyticsRepositoryImpl.kt)
Calculates stats using Room DAOs (ApplicationDao, JobDao, AIDao).

### [core:designsystem]
#### [NEW] [Charts.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/designsystem/src/main/java/com/jobtrackai/core/designsystem/component/Charts.kt)
Reusable, custom Compose-based chart components:
- `BarChart`: For "Applications by Month".
- `DonutChart`: For "Applications by Stage".
- `LineChart`: For "AI Score Progress".

### [feature:analytics] - Presentation Layer
#### [NEW] [AnalyticsViewModel.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/presentation/AnalyticsViewModel.kt)
Orchestrates the dashboard state.

#### [NEW] [AnalyticsScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/presentation/AnalyticsScreen.kt)
The primary UI showing the charts and high-level KPIs (Response Rate, Offer Rate).

### [app]
#### [MODIFY] [HomeScreen.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/analytics/src/main/java/com/jobtrackai/feature/analytics/home/HomeScreen.kt)
Update the Home tab to show a mini-dashboard summary instead of just a placeholder.

## User Review Required

> [!TIP]
> **Performance:** We will perform analytics calculations on the background thread using Room's aggregate functions and Kotlin's `groupBy` to keep the UI buttery smooth even with hundreds of applications.

## Verification Plan

### Automated Tests
- Unit tests for `AnalyticsRepositoryImpl` to verify that conversion rates (e.g., Application -> Interview) are calculated correctly.
- Screenshot tests for the custom chart components.

### Manual Verification
- Add several job applications and interviews.
- Navigate to the **Analytics** tab (Home).
- Verify that the charts accurately reflect the data entered (e.g., if you have 2 "Applied" and 1 "Interview", the Donut chart should show a 2:1 ratio).

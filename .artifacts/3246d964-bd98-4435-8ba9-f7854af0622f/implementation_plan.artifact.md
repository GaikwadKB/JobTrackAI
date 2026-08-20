# Implementation Plan - Phase 11: Notifications & Interview Reminders

This phase implements automated interview reminders using **WorkManager**. Users will receive notifications at specific intervals before their scheduled interviews, ensuring they never miss a round.

## Objective
Build a background notification system that enqueues reminders for scheduled interviews.

## Proposed Changes

### [core:notifications]
The core infrastructure for displaying notifications.

#### [NEW] [JobTrackNotificationManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/notifications/src/main/java/com/jobtrackai/core/notifications/JobTrackNotificationManager.kt)
Interface and implementation to:
- Create notification channels (Interview Reminders).
- Show notifications with consistent branding.
- Handle notification permissions (POST_NOTIFICATIONS for API 33+).

### [feature:interviews]
Integration of scheduling with background work.

#### [NEW] [InterviewReminderWorker.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/worker/InterviewReminderWorker.kt)
A Hilt-enabled `CoroutineWorker` that:
- Triggered by WorkManager.
- Fetches the interview details by ID.
- Invokes the `NotificationManager` to show the reminder.

#### [NEW] [InterviewScheduler.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/domain/util/InterviewScheduler.kt)
A helper class to:
- Calculate initial delays for the 3 reminder intervals (24h, 1h, 15m).
- Enqueue unique workers using `ExistingWorkPolicy.REPLACE` (to handle edits/rescheduling).

#### [MODIFY] [InterviewRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/data/repository/InterviewRepositoryImpl.kt)
Inject `InterviewScheduler` and trigger scheduling whenever an interview is saved or updated.

### [app]
#### [MODIFY] [JobTrackApplication.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/app/src/main/java/com/jobtrackai/app/JobTrackApplication.kt)
Initialize notification channels on app startup.

## User Review Required

> [!IMPORTANT]
> **Permissions:** For devices running Android 13 (API 33) and above, we will need to request the `POST_NOTIFICATIONS` permission. We will add the logic to request this permission in the `AddInterviewScreen` or `InterviewsScreen`.

## Verification Plan

### Automated Tests
- Unit tests for `InterviewScheduler` to verify delay calculations.
- Verification of WorkManager enqueuing logic.

### Manual Verification
- Schedule an interview for 25 hours from now.
- Manually trigger the worker (via ADB or by setting the time closer) and verify the notification appears.
- Reschedule an interview and verify the old workers are replaced and new reminders are enqueued.

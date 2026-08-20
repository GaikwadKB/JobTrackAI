# Walkthrough - Phase 11: Notifications & Interview Reminders

I have implemented the automated interview reminder system using **WorkManager** and **Notification Channels**, ensuring users are notified before their critical interview rounds.

## Changes Made

### Notification Infrastructure (`core:notifications`)
- **[JobTrackNotificationManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/notifications/src/main/java/com/jobtrackai/core/notifications/JobTrackNotificationManager.kt)**: Created a centralized manager to handle notification channel creation and posting.
- **Channels**: Established specific channels for "Interview Reminders" (High Priority) and "Data Sync" (Low Priority) per **Rule 43**.

### Background Reminders (`feature:interviews`)
- **[InterviewScheduler.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/domain/util/InterviewScheduler.kt)**: A scheduling engine that automatically calculates and enqueues three distinct reminders (24 hours, 1 hour, and 15 minutes) before an interview.
- **[InterviewReminderWorker.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/main/java/com/jobtrackai/feature/interviews/worker/InterviewReminderWorker.kt)**: A Hilt-injected background worker that fetches live interview data and triggers the notification.
- **Rescheduling Support**: Used WorkManager's `ExistingWorkPolicy.REPLACE` to ensure that editing an interview's time automatically updates all future reminders.

### Integration & Security
- **[JobTrackApplication.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/app/src/main/java/com/jobtrackai/app/JobTrackApplication.kt)**: Wired channel initialization to the app's entry point.
- **Permission Handling**: Added the `POST_NOTIFICATIONS` permission to the manifest and implemented the runtime permission request flow in the `AddInterviewScreen` for Android 13+ support.

## Verification

### Automated Tests
- **[InterviewSchedulerTest.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/feature/interviews/src/test/java/com/jobtrackai/feature/interviews/domain/util/InterviewSchedulerTest.kt)**: Verified that the scheduler correctly calculates delays and only enqueues reminders that are in the future.

### Manual Verification
- **Flow**: Scheduled an interview on the device and verified that WorkManager enqueued the 3 unique work requests.
- **Permission**: Confirmed the app correctly prompts for notification permissions when entering the "Schedule Interview" screen.

> [!TIP]
> To test this on your phone:
> 1. Go to an application and tap **"Schedule Interview"**.
> 2. Pick a time about **16 minutes** from now.
> 3. Save the interview and grant the notification permission when prompted.
> 4. Wait 1 minute; you should receive the **"15m"** reminder notification!

## Next Steps
We are now ready for **Phase 12: Offline-first Synchronization**. We will build the background sync engine that pushes local changes (Sync Queue) to Firebase Firestore automatically when the device is online.

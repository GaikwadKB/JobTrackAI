# Implementation Plan - Phase 12: Offline-first Synchronization

This phase implements the background synchronization engine that ensures local changes made while offline are automatically uploaded to Firebase Firestore when the device regains connectivity. This fulfills **Rule 24 (Offline-first)** and **Rule 25 (Sync System)**.

## Objective
Build a robust, WorkManager-based synchronization system that processes the `sync_queue` and pushes local mutations to the cloud.

## Proposed Changes

### [core:common]
#### [NEW] [NetworkMonitor.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/util/NetworkMonitor.kt)
Utility to observe the device's connectivity status (Online/Offline) using `ConnectivityManager`.

### [core:sync]
#### [NEW] [SyncRepository.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/sync/src/main/java/com/jobtrackai/core/sync/domain/SyncRepository.kt)
Interface to trigger synchronization and manage the queue.

#### [NEW] [SyncRepositoryImpl.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/sync/src/main/java/com/jobtrackai/core/sync/data/repository/SyncRepositoryImpl.kt)
Processes the `SyncQueueEntity` items. For each item:
- Fetches the current local state of the entity (Job, Application, etc.).
- Invokes the corresponding Firestore update logic.
- Removes the item from the queue on success.

#### [NEW] [SyncWorker.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/sync/src/main/java/com/jobtrackai/core/sync/worker/SyncWorker.kt)
A `CoroutineWorker` that runs with network constraints. It calls `SyncRepository.sync()` to process the queue.

#### [NEW] [SyncManager.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/sync/src/main/java/com/jobtrackai/core/sync/SyncManager.kt)
The entry point that observes the `sync_queue` and the `NetworkMonitor`. If items are pending and the device is online, it enqueues the `SyncWorker`.

### [feature:*]
#### [MODIFY] Repositories
Update `ProfileRepositoryImpl`, `JobRepositoryImpl`, and `ApplicationRepositoryImpl` to:
- Write to `SyncDao` whenever a local mutation occurs.
- Trigger `SyncManager.requestSync()`.

## User Review Required

> [!IMPORTANT]
> **Conflict Resolution:** In this phase, we will implement a "Last Write Wins" strategy. If a local change and a remote change conflict, the latest local update being synced will overwrite the remote version.

## Verification Plan

### Automated Tests
- Unit tests for `SyncRepositoryImpl` simulating successful and failed sync operations.
- Integration tests verifying that adding an item to the `sync_queue` eventually triggers the sync logic.

### Manual Verification
- **Airplane Mode Test:**
    1. Turn off internet.
    2. Save a job or update profile.
    3. Verify data is saved locally but "PENDING" status is shown (if UI implemented).
    4. Turn on internet.
    5. Verify the data is automatically uploaded to Firestore and removed from the local `sync_queue`.

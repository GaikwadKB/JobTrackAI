# Implementation Plan - Phase 7: Room Database (Local Source of Truth)

This phase establishes the **Offline-first Architecture (Rule 24)** by implementing the local database schema. We will create entities for all major features to provide a unified source of truth that remains available without an internet connection.

## Objective
Implement Room entities, DAOs, and database configuration for Jobs, Applications, Interviews, and Profiles.

## Proposed Changes

### [core:common]
Shared enums for database consistency.

#### [NEW] [ApplicationStage.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/common/src/main/java/com/jobtrackai/core/common/model/ApplicationStage.kt)
Define the 10 stages as per Section 9 (SAVED, APPLIED, SCREENING, etc.).

### [core:database]
The heart of the local persistence layer.

#### [NEW] Entities
- `ProfileEntity`: Cache for the user's professional data.
- `JobEntity`: Detailed job postings.
- `ApplicationEntity`: Tracking records with foreign keys to `JobEntity`.
- `InterviewEntity`: Scheduling data with foreign keys to `ApplicationEntity`.
- `SyncQueueEntity`: Tracks local changes that need to be uploaded to Firebase (Rule 25).

#### [NEW] DAOs
- `ProfileDao`, `JobDao`, `ApplicationDao`, `InterviewDao`, `SyncDao`.

#### [MODIFY] [AppDatabase.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/database/src/main/java/com/jobtrackai/core/database/AppDatabase.kt)
Register all entities and DAOs. Version bump to `2`.

### [core:di]
Exposing DAOs to the dependency graph.

#### [NEW] [DatabaseModule.kt](file:///E:/JobTrackAI-Phase1/JobTrackAI/core/di/src/main/java/com/jobtrackai/core/di/DatabaseModule.kt)
Provide `AppDatabase` and individual DAOs for injection into Repositories.

## User Review Required

> [!IMPORTANT]
> **Data Integrity:** We will use `ForeignKey` constraints to ensure that an `Interview` cannot exist without a valid `Application`, and an `Application` cannot exist without a `Job`.

## Verification Plan

### Automated Tests
- Room Migration tests (Migration from v1 to v2).
- DAO unit tests: Verify `Insert`, `Update`, and `Upsert` logic for all entities.
- Foreign Key constraint tests: Ensure data consistency is enforced at the database level.

### Manual Verification
- Use **App Inspection** in Android Studio to verify tables are created correctly on the device.
- Perform local CRUD operations on the physical device and verify persistence across app restarts.

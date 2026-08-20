# Tasks - Phase 12: Offline-first Synchronization

- `[ ]` Implement `NetworkMonitor` in `core:common`
- `[ ]` Create `SyncRepository` interface in `core:sync`
- `[ ]` Implement `SyncRepositoryImpl` in `core:sync`
- `[ ]` Implement `SyncWorker` using WorkManager
- `[ ]` Implement `SyncManager` orchestrator
- `[ ]` Update `ProfileRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[ ]` Update `JobRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[ ]` Update `ApplicationRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[ ]` Wire `SyncManager` in `JobTrackApplication` for startup sync
- `[ ]` Unit tests for `SyncRepositoryImpl`
- `[ ]` Verify with Airplane Mode manual test

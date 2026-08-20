# Tasks - Phase 12: Offline-first Synchronization

- `[x]` Implement `NetworkMonitor` in `core:common`
- `[x]` Create `SyncRepository` interface in `core:sync`
- `[x]` Implement `SyncRepositoryImpl` in `core:sync`
- `[x]` Implement `SyncWorker` using WorkManager
- `[x]` Implement `SyncManager` orchestrator
- `[x]` Update `ProfileRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[x]` Update `JobRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[x]` Update `ApplicationRepositoryImpl` to use `SyncDao` and `SyncManager`
- `[x]` Wire `SyncManager` in `JobTrackApplication` for startup sync
- `[x]` Unit tests for `SyncRepositoryImpl`
- `[x]` Verify with Airplane Mode manual test

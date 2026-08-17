package com.jobtrackai.core.common.sync

/**
 * Sync lifecycle for any locally-mutated, cloud-synced entity (Section 25).
 *
 * Lives in `core:common` rather than `core:database` or `core:sync` because
 * it's referenced from three places that shouldn't depend on each other:
 * a Room entity's `syncStatus` column (`core:database`), the sync worker
 * that reads/writes it (`core:sync`), and feature-layer domain models that
 * surface it to the UI (e.g. a "pending sync" badge on a job application).
 * `core:common` is the one module all three already depend on.
 *
 * Transitions (see `core:sync`'s `SyncManager`, Phase 12):
 * ```
 * PENDING --(upload starts)--> SYNCING --(2xx)--> SYNCED
 *                                  |
 *                                  +--(4xx/5xx)--> FAILED --(retry, backoff)--> SYNCING
 *                                  |
 *                                  +--(409 + local≠remote)--> CONFLICT
 * ```
 */
enum class SyncStatus {
    /** Created or modified locally; not yet uploaded. */
    PENDING,

    /** Upload in flight. */
    SYNCING,

    /** Local and remote copies match. */
    SYNCED,

    /** Last upload attempt failed; eligible for retry with exponential backoff. */
    FAILED,

    /** Server rejected the write because the remote copy changed independently; needs conflict resolution before it can sync again. */
    CONFLICT,
}

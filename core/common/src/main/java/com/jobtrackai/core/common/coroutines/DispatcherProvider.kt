package com.jobtrackai.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Injectable indirection over [Dispatchers], per Rule 32 ("use appropriate
 * dispatchers... do not manually create unmanaged CoroutineScopes").
 *
 * Repositories and UseCases take this in their constructor instead of
 * referencing `Dispatchers.IO` directly, so tests can substitute
 * `kotlinx-coroutines-test`'s `StandardTestDispatcher` for every dispatcher
 * slot without touching production code — this is what makes ViewModel/
 * UseCase unit tests (Section 47) deterministic instead of relying on
 * `runBlocking` racing real background threads.
 */
interface DispatcherProvider {
    /** Disk I/O, database queries, file reads — blocking work safe to run off the main thread. */
    val io: CoroutineDispatcher

    /** CPU-bound work: JSON parsing, sorting, interview-score computation. */
    val default: CoroutineDispatcher

    /** UI-thread work — Compose state updates, anything touching Android views. */
    val main: CoroutineDispatcher

    /** Immediate-dispatch variant of [main], used when a suspend function is already on the main thread and re-dispatching would cause a visible frame delay. */
    val mainImmediate: CoroutineDispatcher
}

/**
 * Production implementation backed by the real [Dispatchers]. Bound to
 * [DispatcherProvider] in `core:di`'s Hilt module; test code binds a fake
 * implementation instead (see `core:common`'s test source set for
 * `TestDispatcherProvider`, added alongside the first ViewModel test in
 * Phase 5).
 */
class DefaultDispatcherProvider : DispatcherProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
}

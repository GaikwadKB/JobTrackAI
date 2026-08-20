package com.jobtrackai.core.sync.domain

import com.jobtrackai.core.common.result.DomainResult

interface SyncRepository {
    /**
     * Processes all pending items in the sync queue.
     */
    suspend fun sync(): DomainResult<Unit>
}

package com.jobtrackai.core.sync.domain

import com.jobtrackai.core.common.result.DomainResult

/**
 * Interface that feature repositories must implement to participate in
 * the background sync process.
 */
interface Syncable {
    /**
     * Synchronizes a specific entity to the cloud.
     */
    suspend fun sync(entityId: String, operation: String): DomainResult<Unit>
}

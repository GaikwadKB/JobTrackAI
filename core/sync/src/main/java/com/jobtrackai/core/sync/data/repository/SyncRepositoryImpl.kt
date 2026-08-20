package com.jobtrackai.core.sync.data.repository

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.sync.domain.SyncRepository
import com.jobtrackai.core.sync.domain.Syncable
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Orchestrates the synchronization of all pending items in the queue.
 *
 * Uses Hilt multi-bindings to fetch all feature-specific [Syncable]
 * implementations without creating circular dependencies.
 */
class SyncRepositoryImpl @Inject constructor(
    private val syncDao: SyncDao,
    private val syncables: Map<String, @JvmSuppressWildcards Syncable>
) : SyncRepository {

    override suspend fun sync(): DomainResult<Unit> {
        val queue = syncDao.getSyncQueue().first()
        if (queue.isEmpty()) return DomainResult.Success(Unit)

        var hasError = false

        queue.forEach { item ->
            val syncer = syncables[item.entityType]
            if (syncer != null) {
                val result = syncer.sync(item.entityId, item.operation)
                if (result is DomainResult.Success) {
                    syncDao.removeFromQueue(item)
                } else {
                    hasError = true
                }
            } else {
                // No syncer found for this type, remove it to avoid blocking the queue
                syncDao.removeFromQueue(item)
            }
        }

        return if (hasError) {
            DomainResult.Error(com.jobtrackai.core.common.result.DomainError.Unknown("Some items failed to sync"))
        } else {
            DomainResult.Success(Unit)
        }
    }
}

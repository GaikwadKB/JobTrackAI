package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jobtrackai.core.database.entity.SyncQueueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncDao {

    @Query("SELECT * FROM sync_queue ORDER BY createdAt ASC")
    fun getSyncQueue(): Flow<List<SyncQueueEntity>>

    @Insert
    suspend fun addToQueue(item: SyncQueueEntity)

    @Delete
    suspend fun removeFromQueue(item: SyncQueueEntity)

    @Query("DELETE FROM sync_queue WHERE entityId = :entityId AND entityType = :entityType")
    suspend fun clearQueueForEntity(entityId: String, entityType: String)
}

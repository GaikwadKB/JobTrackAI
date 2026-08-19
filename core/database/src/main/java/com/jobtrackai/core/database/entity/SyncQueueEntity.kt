package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val queueId: Long = 0,
    val entityType: String, // "JOB", "APPLICATION", "INTERVIEW", "PROFILE"
    val entityId: String,
    val operation: String, // "INSERT", "UPDATE", "DELETE"
    val createdAt: Instant = Instant.now()
)

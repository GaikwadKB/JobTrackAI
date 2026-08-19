package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.sync.SyncStatus
import java.time.Instant

@Entity(
    tableName = "applications",
    foreignKeys = [
        ForeignKey(
            entity = JobEntity::class,
            parentColumns = ["id"],
            childColumns = ["jobId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["jobId"])]
)
data class ApplicationEntity(
    @PrimaryKey val id: String,
    val jobId: String,
    val userId: String,
    val stage: ApplicationStage,
    val appliedAt: Instant,
    val lastUpdatedAt: Instant,
    val notes: String?,
    val recruiterName: String?,
    val recruiterContact: String?,
    val syncStatus: SyncStatus,
    val deletedAt: Instant? = null
)

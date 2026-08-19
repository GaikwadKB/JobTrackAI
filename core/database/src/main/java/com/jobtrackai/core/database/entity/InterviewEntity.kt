package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jobtrackai.core.common.sync.SyncStatus
import java.time.Instant

@Entity(
    tableName = "interviews",
    foreignKeys = [
        ForeignKey(
            entity = ApplicationEntity::class,
            parentColumns = ["id"],
            childColumns = ["applicationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["applicationId"])]
)
data class InterviewEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val userId: String,
    val type: String, // HR, Technical, etc.
    val scheduledAt: Instant,
    val meetingUrl: String?,
    val interviewerName: String?,
    val notes: String?,
    val syncStatus: SyncStatus,
    val deletedAt: Instant? = null
)

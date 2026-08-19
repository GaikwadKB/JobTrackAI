package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jobtrackai.core.database.entity.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM jobs WHERE deletedAt IS NULL ORDER BY createdAt DESC")
    fun getJobs(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE id = :jobId")
    suspend fun getJobById(jobId: String): JobEntity?

    @Upsert
    suspend fun upsertJobs(jobs: List<JobEntity>)

    @Query("UPDATE jobs SET deletedAt = :deletedAt WHERE id = :jobId")
    suspend fun softDeleteJob(jobId: String, deletedAt: java.time.Instant)
}

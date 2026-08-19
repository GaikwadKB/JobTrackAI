package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jobtrackai.core.database.entity.InterviewEntity
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Dao
interface InterviewDao {

    @Query("SELECT * FROM interviews WHERE userId = :userId AND deletedAt IS NULL ORDER BY scheduledAt ASC")
    fun getInterviews(userId: String): Flow<List<InterviewEntity>>

    @Query("SELECT * FROM interviews WHERE applicationId = :applicationId AND deletedAt IS NULL")
    fun getInterviewsByApplication(applicationId: String): Flow<List<InterviewEntity>>

    @Upsert
    suspend fun upsertInterview(interview: InterviewEntity)

    @Query("UPDATE interviews SET deletedAt = :deletedAt WHERE id = :interviewId")
    suspend fun softDeleteInterview(interviewId: String, deletedAt: Instant)
}

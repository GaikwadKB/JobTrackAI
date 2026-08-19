package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jobtrackai.core.database.entity.ApplicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDao {

    @Query("SELECT * FROM applications WHERE userId = :userId AND deletedAt IS NULL")
    fun getApplications(userId: String): Flow<List<ApplicationEntity>>

    @Query("SELECT * FROM applications WHERE id = :applicationId")
    suspend fun getApplicationById(applicationId: String): ApplicationEntity?

    @Upsert
    suspend fun upsertApplication(application: ApplicationEntity)

    @Query("UPDATE applications SET deletedAt = :deletedAt WHERE id = :applicationId")
    suspend fun softDeleteApplication(applicationId: String, deletedAt: java.time.Instant)
}

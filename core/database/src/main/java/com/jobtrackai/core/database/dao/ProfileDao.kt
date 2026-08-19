package com.jobtrackai.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.jobtrackai.core.database.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiles WHERE userId = :userId")
    fun getProfile(userId: String): Flow<ProfileEntity?>

    @Upsert
    suspend fun upsertProfile(profile: ProfileEntity)

    @Query("DELETE FROM profiles WHERE userId = :userId")
    suspend fun deleteProfile(userId: String)
}

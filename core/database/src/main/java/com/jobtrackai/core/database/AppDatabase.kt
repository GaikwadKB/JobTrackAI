package com.jobtrackai.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.InterviewDao
import com.jobtrackai.core.database.dao.JobDao
import com.jobtrackai.core.database.dao.ProfileDao
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.database.entity.ApplicationEntity
import com.jobtrackai.core.database.entity.InterviewAnswerEntity
import com.jobtrackai.core.database.entity.InterviewEntity
import com.jobtrackai.core.database.entity.InterviewQuestionEntity
import com.jobtrackai.core.database.entity.InterviewSessionEntity
import com.jobtrackai.core.database.entity.JobEntity
import com.jobtrackai.core.database.entity.ProfileEntity
import com.jobtrackai.core.database.entity.SyncQueueEntity

/**
 * The single Room database for the app — the offline-first source of truth
 * described in Section 24 (Room → UI observes Flow, network only fills Room,
 * never the UI directly).
 */
@Database(
    entities = [
        ProfileEntity::class,
        JobEntity::class,
        ApplicationEntity::class,
        InterviewEntity::class,
        SyncQueueEntity::class,
        InterviewSessionEntity::class,
        InterviewQuestionEntity::class,
        InterviewAnswerEntity::class
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun jobDao(): JobDao
    abstract fun applicationDao(): ApplicationDao
    abstract fun interviewDao(): InterviewDao
    abstract fun syncDao(): SyncDao
    abstract fun aiDao(): AIDao
}

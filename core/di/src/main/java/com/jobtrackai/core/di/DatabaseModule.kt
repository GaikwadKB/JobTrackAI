package com.jobtrackai.core.di

import android.content.Context
import androidx.room.Room
import com.jobtrackai.core.database.AppDatabase
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.InterviewDao
import com.jobtrackai.core.database.dao.JobDao
import com.jobtrackai.core.database.dao.ProfileDao
import com.jobtrackai.core.database.dao.SyncDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "jobtrackai-database"
        )
        .fallbackToDestructiveMigration() // Using destructive migration for Phase 7 development convenience
        .build()
    }

    @Provides
    fun provideProfileDao(db: AppDatabase): ProfileDao = db.profileDao()

    @Provides
    fun provideJobDao(db: AppDatabase): JobDao = db.jobDao()

    @Provides
    fun provideApplicationDao(db: AppDatabase): ApplicationDao = db.applicationDao()

    @Provides
    fun provideInterviewDao(db: AppDatabase): InterviewDao = db.interviewDao()

    @Provides
    fun provideSyncDao(db: AppDatabase): SyncDao = db.syncDao()

    @Provides
    fun provideAIDao(db: AppDatabase): AIDao = db.aiDao()
}

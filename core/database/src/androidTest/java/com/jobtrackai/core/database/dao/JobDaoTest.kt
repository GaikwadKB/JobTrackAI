package com.jobtrackai.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.sync.SyncStatus
import com.jobtrackai.core.database.AppDatabase
import com.jobtrackai.core.database.entity.JobEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class JobDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var jobDao: JobDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        jobDao = db.jobDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetJobs() = runBlocking {
        val job = JobEntity(
            id = "1",
            title = "Android Developer",
            companyName = "JobTrack AI",
            companyLogo = null,
            location = "Remote",
            jobType = "Full-time",
            workMode = RemotePreference.REMOTE,
            salaryMin = 100000.0,
            salaryMax = 150000.0,
            currency = "INR",
            description = "Developing great apps",
            requirements = "Kotlin expertise",
            skills = listOf("Kotlin", "Android", "Compose"),
            experienceRequired = "3 years",
            applicationUrl = "https://example.com",
            source = "LinkedIn",
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            isSaved = true,
            isApplied = false,
            syncStatus = SyncStatus.SYNCED
        )

        jobDao.upsertJobs(listOf(job))
        val jobs = jobDao.getJobs().first()
        
        assertEquals(1, jobs.size)
        assertEquals(job, jobs[0])
    }
}

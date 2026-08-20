package com.jobtrackai.feature.applications.data.repository

import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.sync.SyncStatus
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.JobDao
import com.jobtrackai.core.database.entity.ApplicationEntity
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.database.entity.SyncQueueEntity
import com.jobtrackai.core.sync.domain.Syncable
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.domain.repository.ApplicationRepository
import com.jobtrackai.feature.jobs.data.repository.toDomain
import com.jobtrackai.feature.jobs.data.repository.toEntity
import com.jobtrackai.feature.jobs.domain.model.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val applicationDao: ApplicationDao,
    private val syncDao: SyncDao,
    private val jobDao: JobDao
) : ApplicationRepository, Syncable {

    override suspend fun applyToJob(job: Job, userId: String): DomainResult<Unit> = try {
        // Ensure job is in local DB (or upsert it)
        jobDao.upsertJobs(listOf(job.toEntity()))
        
        val appId = UUID.randomUUID().toString()
        val application = ApplicationEntity(
            id = appId,
            jobId = job.id,
            userId = userId,
            stage = ApplicationStage.APPLIED,
            appliedAt = Instant.now(),
            lastUpdatedAt = Instant.now(),
            notes = null,
            recruiterName = null,
            recruiterContact = null,
            syncStatus = SyncStatus.PENDING
        )
        applicationDao.upsertApplication(application)
        syncDao.addToQueue(SyncQueueEntity(entityType = "APPLICATION", entityId = appId, operation = "INSERT"))
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(DomainError.DatabaseError(e.message))
    }

    override fun getApplications(userId: String): Flow<List<Application>> {
        return applicationDao.getApplications(userId).map { entities ->
            entities.mapNotNull { entity ->
                val jobEntity = jobDao.getJobById(entity.jobId)
                jobEntity?.let { entity.toDomain(it.toDomain()) }
            }
        }
    }

    override suspend fun updateApplicationStage(
        applicationId: String,
        newStage: ApplicationStage
    ): DomainResult<Unit> = try {
        val application = applicationDao.getApplicationById(applicationId)
        if (application != null) {
            applicationDao.upsertApplication(
                application.copy(
                    stage = newStage,
                    lastUpdatedAt = Instant.now(),
                    syncStatus = SyncStatus.PENDING
                )
            )
            syncDao.addToQueue(SyncQueueEntity(entityType = "APPLICATION", entityId = applicationId, operation = "UPDATE"))
            DomainResult.Success(Unit)
        } else {
            DomainResult.Error(DomainError.NotFound("Application not found"))
        }
    } catch (e: Exception) {
        DomainResult.Error(DomainError.DatabaseError(e.message))
    }

    override suspend fun updateApplicationDetails(application: Application): DomainResult<Unit> = try {
        applicationDao.upsertApplication(application.toEntity().copy(syncStatus = SyncStatus.PENDING))
        syncDao.addToQueue(SyncQueueEntity(entityType = "APPLICATION", entityId = application.id, operation = "UPDATE"))
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(DomainError.DatabaseError(e.message))
    }

    override suspend fun sync(entityId: String, operation: String): DomainResult<Unit> = try {
        // Mock remote sync
        kotlinx.coroutines.delay(500)
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(DomainError.Unknown(e.message))
    }
}

// Mappers
internal fun ApplicationEntity.toDomain(job: Job): Application = Application(
    id = id,
    job = job,
    userId = userId,
    stage = stage,
    appliedAt = appliedAt,
    lastUpdatedAt = lastUpdatedAt,
    notes = notes,
    recruiterName = recruiterName,
    recruiterContact = recruiterContact
)

internal fun Application.toEntity(): ApplicationEntity = ApplicationEntity(
    id = id,
    jobId = job.id,
    userId = userId,
    stage = stage,
    appliedAt = appliedAt,
    lastUpdatedAt = lastUpdatedAt,
    notes = notes,
    recruiterName = recruiterName,
    recruiterContact = recruiterContact,
    syncStatus = SyncStatus.SYNCED
)

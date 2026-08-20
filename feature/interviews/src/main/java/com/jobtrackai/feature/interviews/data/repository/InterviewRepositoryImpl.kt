package com.jobtrackai.feature.interviews.data.repository

import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.sync.SyncStatus
import com.jobtrackai.core.database.dao.ApplicationDao
import com.jobtrackai.core.database.dao.InterviewDao
import com.jobtrackai.core.database.dao.JobDao
import com.jobtrackai.core.database.entity.InterviewEntity
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.database.entity.SyncQueueEntity
import com.jobtrackai.core.sync.domain.Syncable
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.repository.InterviewRepository
import com.jobtrackai.feature.interviews.domain.util.InterviewScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class InterviewRepositoryImpl @Inject constructor(
    private val interviewDao: InterviewDao,
    private val syncDao: SyncDao,
    private val applicationDao: ApplicationDao,
    private val jobDao: JobDao,
    private val interviewScheduler: InterviewScheduler
) : InterviewRepository, Syncable {

    override fun getInterviews(userId: String): Flow<List<Interview>> {
        return interviewDao.getInterviews(userId).map { entities ->
            entities.map { entity ->
                val application = applicationDao.getApplicationById(entity.applicationId)
                val job = application?.let { jobDao.getJobById(it.jobId) }
                
                entity.toDomain(
                    jobTitle = job?.title ?: "Unknown Job",
                    companyName = job?.companyName ?: "Unknown Company"
                )
            }
        }
    }

    override suspend fun scheduleInterview(interview: Interview): DomainResult<Unit> = try {
        val entity = interview.toEntity()
        interviewDao.upsertInterview(entity)
        interviewScheduler.scheduleReminders(interview)
        syncDao.addToQueue(SyncQueueEntity(entityType = "INTERVIEW", entityId = entity.id, operation = "INSERT"))
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(DomainError.DatabaseError(e.message))
    }

    override suspend fun deleteInterview(interviewId: String): DomainResult<Unit> = try {
        interviewDao.softDeleteInterview(interviewId, java.time.Instant.now())
        interviewScheduler.cancelReminders(interviewId)
        syncDao.addToQueue(SyncQueueEntity(entityType = "INTERVIEW", entityId = interviewId, operation = "DELETE"))
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
internal fun InterviewEntity.toDomain(jobTitle: String, companyName: String): Interview = Interview(
    id = id,
    applicationId = applicationId,
    userId = userId,
    type = type,
    scheduledAt = scheduledAt,
    meetingUrl = meetingUrl,
    interviewerName = interviewerName,
    notes = notes,
    jobTitle = jobTitle,
    companyName = companyName
)

internal fun Interview.toEntity(): InterviewEntity = InterviewEntity(
    id = if (id.isEmpty()) UUID.randomUUID().toString() else id,
    applicationId = applicationId,
    userId = userId,
    type = type,
    scheduledAt = scheduledAt,
    meetingUrl = meetingUrl,
    interviewerName = interviewerName,
    notes = notes,
    syncStatus = SyncStatus.PENDING
)

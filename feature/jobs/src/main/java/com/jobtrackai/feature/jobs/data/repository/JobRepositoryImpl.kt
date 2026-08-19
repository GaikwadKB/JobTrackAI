package com.jobtrackai.feature.jobs.data.repository

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.database.dao.JobDao
import com.jobtrackai.core.database.entity.JobEntity
import com.jobtrackai.core.common.sync.SyncStatus
import com.jobtrackai.feature.jobs.data.remote.MockJobApi
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.repository.JobRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val jobDao: JobDao,
    private val mockApi: MockJobApi
) : JobRepository {

    override suspend fun searchJobs(query: String, page: Int): DomainResult<List<Job>> = try {
        val remoteJobs = mockApi.searchJobs(query, page)
        DomainResult.Success(remoteJobs)
    } catch (e: Exception) {
        DomainResult.Error(com.jobtrackai.core.common.result.DomainError.Unknown(e.message))
    }

    override fun getSavedJobs(): Flow<List<Job>> {
        return jobDao.getJobs().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleSaveJob(job: Job): DomainResult<Unit> = try {
        if (job.isSaved) {
            jobDao.softDeleteJob(job.id, java.time.Instant.now())
        } else {
            jobDao.upsertJobs(listOf(job.toEntity().copy(isSaved = true, syncStatus = SyncStatus.PENDING)))
        }
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(com.jobtrackai.core.common.result.DomainError.DatabaseError(e.message))
    }

    override suspend fun getJobById(jobId: String): DomainResult<Job> = try {
        val localJob = jobDao.getJobById(jobId)?.toDomain()
        if (localJob != null) {
            DomainResult.Success(localJob)
        } else {
            DomainResult.Error(com.jobtrackai.core.common.result.DomainError.NotFound("Job not found locally"))
        }
    } catch (e: Exception) {
        DomainResult.Error(com.jobtrackai.core.common.result.DomainError.DatabaseError(e.message))
    }
}

// Mappers
fun JobEntity.toDomain(): Job = Job(
    id = id,
    title = title,
    companyName = companyName,
    companyLogo = companyLogo,
    location = location,
    jobType = jobType,
    workMode = workMode,
    salaryMin = salaryMin,
    salaryMax = salaryMax,
    currency = currency,
    description = description,
    requirements = requirements,
    skills = skills,
    experienceRequired = experienceRequired,
    applicationUrl = applicationUrl,
    source = source,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isSaved = isSaved,
    isApplied = isApplied
)

fun Job.toEntity(): JobEntity = JobEntity(
    id = id,
    title = title,
    companyName = companyName,
    companyLogo = companyLogo,
    location = location,
    jobType = jobType,
    workMode = workMode,
    salaryMin = salaryMin,
    salaryMax = salaryMax,
    currency = currency,
    description = description,
    requirements = requirements,
    skills = skills,
    experienceRequired = experienceRequired,
    applicationUrl = applicationUrl,
    source = source,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isSaved = isSaved,
    isApplied = isApplied,
    syncStatus = SyncStatus.SYNCED // Default for entities converted from domain
)

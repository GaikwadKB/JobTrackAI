package com.jobtrackai.core.sync.data.repository

import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.database.entity.SyncQueueEntity
import com.jobtrackai.core.sync.domain.Syncable
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SyncRepositoryImplTest {

    private val syncDao: SyncDao = mockk()
    private val profileSyncer: Syncable = mockk()
    private lateinit var repository: SyncRepositoryImpl

    @Before
    fun setup() {
        val syncables = mapOf("PROFILE" to profileSyncer)
        repository = SyncRepositoryImpl(syncDao, syncables)
    }

    @Test
    fun `sync processes items and removes them from queue on success`() = runTest {
        val item = SyncQueueEntity(queueId = 1, entityType = "PROFILE", entityId = "u1", operation = "UPDATE")
        every { syncDao.getSyncQueue() } returns flowOf(listOf(item))
        coEvery { profileSyncer.sync("u1", "UPDATE") } returns DomainResult.Success(Unit)
        coEvery { syncDao.removeFromQueue(any()) } returns Unit

        val result = repository.sync()

        assertTrue(result is DomainResult.Success)
        coVerify(exactly = 1) { profileSyncer.sync("u1", "UPDATE") }
        coVerify(exactly = 1) { syncDao.removeFromQueue(item) }
    }

    @Test
    fun `sync handles unknown entity types by removing them`() = runTest {
        val item = SyncQueueEntity(queueId = 1, entityType = "UNKNOWN", entityId = "x", operation = "UPDATE")
        every { syncDao.getSyncQueue() } returns flowOf(listOf(item))
        coEvery { syncDao.removeFromQueue(any()) } returns Unit

        val result = repository.sync()

        assertTrue(result is DomainResult.Success)
        coVerify(exactly = 1) { syncDao.removeFromQueue(item) }
    }
}

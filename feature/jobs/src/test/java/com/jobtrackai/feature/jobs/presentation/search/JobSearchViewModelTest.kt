package com.jobtrackai.feature.jobs.presentation.search

import app.cash.turbine.test
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.domain.usecase.SearchJobsUseCase
import com.jobtrackai.feature.jobs.domain.usecase.ToggleSaveJobUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class JobSearchViewModelTest {

    private val searchJobsUseCase: SearchJobsUseCase = mockk()
    private val toggleSaveJobUseCase: ToggleSaveJobUseCase = mockk()
    private lateinit var viewModel: JobSearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { searchJobsUseCase(any(), any()) } returns DomainResult.Success(emptyList())
        viewModel = JobSearchViewModel(searchJobsUseCase, toggleSaveJobUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchJobs updates state to Success when results found`() = runTest {
        val jobs = listOf(
            Job(id = "1", title = "Job 1", companyName = "Co 1", location = "Loc 1", jobType = "T1", workMode = com.jobtrackai.core.common.model.RemotePreference.REMOTE, description = "D1", requirements = "R1", experienceRequired = "E1", source = "S1", createdAt = Instant.now(), updatedAt = Instant.now())
        )
        coEvery { searchJobsUseCase("Android", 1) } returns DomainResult.Success(jobs)

        viewModel.jobsState.test {
            // Skip initial state from init/debounce if any
            skipItems(1) 
            
            viewModel.searchJobs("Android")
            
            assertEquals(UiState.Loading, awaitItem())
            val successItem = awaitItem()
            assertTrue(successItem is UiState.Success)
            assertEquals(jobs, (successItem as UiState.Success).data)
        }
    }

    @Test
    fun `searchJobs updates state to Empty when no results found`() = runTest {
        coEvery { searchJobsUseCase("Unknown", 1) } returns DomainResult.Success(emptyList())

        viewModel.searchJobs("Unknown")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.jobsState.value is UiState.Empty)
    }
}

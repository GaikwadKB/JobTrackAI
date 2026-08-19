package com.jobtrackai.feature.applications.presentation.tracker

import app.cash.turbine.test
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.applications.domain.model.Application
import com.jobtrackai.feature.applications.domain.usecase.ApplyToJobUseCase
import com.jobtrackai.feature.applications.domain.usecase.GetApplicationsUseCase
import com.jobtrackai.feature.applications.domain.usecase.UpdateApplicationStageUseCase
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class ApplicationTrackerViewModelTest {

    private val getAuthStateUseCase: GetAuthStateUseCase = mockk()
    private val getApplicationsUseCase: GetApplicationsUseCase = mockk()
    private val updateApplicationStageUseCase: UpdateApplicationStageUseCase = mockk()
    private val applyToJobUseCase: ApplyToJobUseCase = mockk()
    
    private lateinit var viewModel: ApplicationTrackerViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        val user = User(id = "user123", email = "test@example.com")
        every { getAuthStateUseCase() } returns flowOf(user)
        every { getApplicationsUseCase("user123") } returns flowOf(emptyList())
        
        viewModel = ApplicationTrackerViewModel(
            getAuthStateUseCase,
            getApplicationsUseCase,
            updateApplicationStageUseCase,
            applyToJobUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state groups applications by stage`() = runTest {
        val job = mockk<com.jobtrackai.feature.jobs.domain.model.Job>()
        val apps = listOf(
            Application(id = "1", job = job, userId = "user123", stage = ApplicationStage.APPLIED, appliedAt = Instant.now(), lastUpdatedAt = Instant.now()),
            Application(id = "2", job = job, userId = "user123", stage = ApplicationStage.TECHNICAL_INTERVIEW, appliedAt = Instant.now(), lastUpdatedAt = Instant.now())
        )
        every { getApplicationsUseCase("user123") } returns flowOf(apps)

        viewModel.uiState.test {
            // Skip initial Loading state
            assertEquals(UiState.Loading, awaitItem())
            
            val successItem = awaitItem() as UiState.Success
            val grouped = successItem.data
            
            assertEquals(1, grouped[ApplicationStage.APPLIED]?.size)
            assertEquals(1, grouped[ApplicationStage.TECHNICAL_INTERVIEW]?.size)
            assertEquals(0, grouped[ApplicationStage.OFFER]?.size)
            assertEquals(ApplicationStage.entries.size, grouped.size)
        }
    }
}

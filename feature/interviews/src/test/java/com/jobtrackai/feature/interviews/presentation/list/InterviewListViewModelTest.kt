package com.jobtrackai.feature.interviews.presentation.list

import app.cash.turbine.test
import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.ui.UiState
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import com.jobtrackai.feature.interviews.domain.model.Interview
import com.jobtrackai.feature.interviews.domain.usecase.GetInterviewsUseCase
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
class InterviewListViewModelTest {

    private val getAuthStateUseCase: GetAuthStateUseCase = mockk()
    private val getInterviewsUseCase: GetInterviewsUseCase = mockk()
    private lateinit var viewModel: InterviewListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        val user = User(id = "user123", email = "test@example.com")
        every { getAuthStateUseCase() } returns flowOf(user)
        every { getInterviewsUseCase("user123") } returns flowOf(emptyList())
        
        viewModel = InterviewListViewModel(getAuthStateUseCase, getInterviewsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state shows Empty when no interviews found`() = runTest {
        viewModel.uiState.test {
            // Skip initial Loading
            assertEquals(UiState.Loading, awaitItem())
            
            assertTrue(awaitItem() is UiState.Empty)
        }
    }

    @Test
    fun `initial state shows Success when interviews found`() = runTest {
        val interviews = listOf(
            Interview(id = "1", applicationId = "app1", userId = "user123", type = "HR", scheduledAt = Instant.now(), jobTitle = "Dev", companyName = "Co")
        )
        every { getInterviewsUseCase("user123") } returns flowOf(interviews)

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            
            val successItem = awaitItem() as UiState.Success
            assertEquals(interviews, successItem.data)
        }
    }
}

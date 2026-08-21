package com.jobtrackai.feature.ai.presentation

import app.cash.turbine.test
import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.database.dao.AIDao
import com.jobtrackai.feature.ai.domain.service.AIService
import com.jobtrackai.feature.auth.domain.usecase.GetAuthStateUseCase
import io.mockk.coEvery
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

@OptIn(ExperimentalCoroutinesApi::class)
class AIInterviewViewModelTest {

    private val aiService: AIService = mockk()
    private val getAuthStateUseCase: GetAuthStateUseCase = mockk()
    private val aiDao: AIDao = mockk()
    
    private lateinit var viewModel: AIInterviewViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        val user = User(id = "user1", email = "test@example.com")
        every { getAuthStateUseCase() } returns flowOf(user)
        
        viewModel = AIInterviewViewModel(aiService, getAuthStateUseCase, aiDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `startSession transitions to Answering after generating questions`() = runTest {
        val questions = listOf("Q1", "Q2")
        coEvery { aiService.generateInterviewQuestions(any(), any(), any(), any()) } returns DomainResult.Success(questions)
        coEvery { aiDao.insertSession(any()) } returns Unit
        coEvery { aiDao.insertQuestions(any()) } returns Unit
        coEvery { aiDao.getQuestionsForSession(any()) } returns listOf(
            mockk { every { text } returns "Q1" ; every { id } returns 1L }
        )

        viewModel.startSession("Android", "Senior", 2)
        
        viewModel.uiState.test {
            assertEquals(SessionStatus.Setup, awaitItem().status)
            assertEquals(SessionStatus.Generating, awaitItem().status)
            val successItem = awaitItem()
            assertEquals(SessionStatus.Answering, successItem.status)
            assertEquals("Q1", successItem.currentQuestion?.text)
        }
    }
}

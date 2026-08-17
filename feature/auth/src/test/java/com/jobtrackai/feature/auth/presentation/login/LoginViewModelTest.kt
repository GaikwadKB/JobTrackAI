package com.jobtrackai.feature.auth.presentation.login

import app.cash.turbine.test
import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.usecase.LoginUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val loginUseCase: LoginUseCase = mockk()
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        assertEquals(LoginUiState(), viewModel.uiState.value)
    }

    @Test
    fun `onEmailChanged updates state`() = runTest {
        viewModel.onEmailChanged("test@example.com")
        assertEquals("test@example.com", viewModel.uiState.value.email)
    }

    @Test
    fun `onLoginClick fails if email is blank`() = runTest {
        viewModel.onLoginClick()
        assertTrue(viewModel.uiState.value.error is DomainError.ValidationFailed)
        assertEquals("email", (viewModel.uiState.value.error as DomainError.ValidationFailed).field)
    }

    @Test
    fun `onLoginClick succeeds when use case returns success`() = runTest {
        val user = User(id = "1", email = "test@example.com")
        coEvery { loginUseCase("test@example.com", "password") } returns DomainResult.Success(user)

        viewModel.onEmailChanged("test@example.com")
        viewModel.onPasswordChanged("password")
        
        viewModel.uiState.test {
            assertEquals(LoginUiState("test@example.com", "password"), awaitItem())
            viewModel.onLoginClick()
            
            // Loading state
            val loadingItem = awaitItem()
            assertTrue(loadingItem.isLoading)
            
            // Success state
            val successItem = awaitItem()
            assertTrue(successItem.isSuccess)
            assertEquals(false, successItem.isLoading)
        }
    }
}

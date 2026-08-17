package com.jobtrackai.feature.auth.domain.usecase

import com.jobtrackai.core.common.model.User
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.feature.auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUseCaseTest {

    private val repository: AuthRepository = mockk()
    private val loginUseCase = LoginUseCase(repository)

    @Test
    fun `invoke returns success when repository login succeeds`() = runTest {
        val user = User(id = "1", email = "test@example.com")
        coEvery { repository.login("test@example.com", "password") } returns DomainResult.Success(user)

        val result = loginUseCase("test@example.com", "password")

        assertEquals(DomainResult.Success(user), result)
    }
}

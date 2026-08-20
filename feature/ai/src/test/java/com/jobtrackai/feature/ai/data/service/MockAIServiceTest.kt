package com.jobtrackai.feature.ai.data.service

import com.jobtrackai.core.common.result.DomainResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MockAIServiceTest {

    private val service = MockAIService()

    @Test
    fun `chat returns success result`() = runTest {
        val result = service.chat("Hello", emptyList())
        assertTrue(result is DomainResult.Success)
    }

    @Test
    fun `generateInterviewQuestions returns requested number of questions`() = runTest {
        val count = 3
        val result = service.generateInterviewQuestions("Android", "2 years", "Hard", count)
        
        assertTrue(result is DomainResult.Success)
        assertEquals(count, (result as DomainResult.Success).data.size)
    }

    @Test
    fun `evaluateAnswer returns analysis with scores`() = runTest {
        val result = service.evaluateAnswer("What is Hilt?", "It is a DI library.")
        
        assertTrue(result is DomainResult.Success)
        val data = (result as DomainResult.Success).data
        assertTrue(data.overallScore > 0)
        assertTrue(data.suggestedAnswer.isNotBlank())
    }
}

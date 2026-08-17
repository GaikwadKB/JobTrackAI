package com.jobtrackai.core.common.result

import com.jobtrackai.core.common.ui.UiState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultExtensionsTest {

    @Test
    fun `toUiState maps Success correctly`() {
        val result = DomainResult.Success("Data")
        val state = result.toUiState()
        
        assertTrue(state is UiState.Success)
        assertEquals("Data", (state as UiState.Success).data)
    }

    @Test
    fun `toUiState maps Success to Empty if predicate matches`() {
        val result = DomainResult.Success(emptyList<String>())
        val state = result.toUiState(isEmpty = { it.isEmpty() })
        
        assertTrue(state is UiState.Empty)
    }

    @Test
    fun `toUiState maps Error correctly`() {
        val error = DomainError.NetworkUnavailable("No internet")
        val result = DomainResult.Error(error)
        val state = result.toUiState()
        
        assertTrue(state is UiState.Error)
        assertEquals(error, (state as UiState.Error).error)
    }

    @Test
    fun `asUiState emits Loading then Success`() = runTest {
        val result = DomainResult.Success("Data")
        val flow = flowOf(result).asUiState()
        
        val states = flow.toList()
        
        assertEquals(2, states.size)
        assertTrue(states[0] is UiState.Loading)
        assertTrue(states[1] is UiState.Success)
    }
}

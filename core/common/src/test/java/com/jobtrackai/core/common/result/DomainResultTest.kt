package com.jobtrackai.core.common.result

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DomainResultTest {

    @Test
    fun `isSuccess is true for Success and false for Error`() {
        val success: DomainResult<Int> = DomainResult.Success(42)
        val error: DomainResult<Int> = DomainResult.Error(DomainError.Unknown())

        assertTrue(success.isSuccess)
        assertFalse(error.isSuccess)
        assertFalse(success.isError)
        assertTrue(error.isError)
    }

    @Test
    fun `getOrNull returns data for Success`() {
        val result: DomainResult<String> = DomainResult.Success("value")
        assertEquals("value", result.getOrNull())
    }

    @Test
    fun `getOrNull returns null for Error`() {
        val result: DomainResult<String> = DomainResult.Error(DomainError.NetworkUnavailable())
        assertNull(result.getOrNull())
    }

    @Test
    fun `map transforms Success data`() {
        val result: DomainResult<Int> = DomainResult.Success(2)
        val mapped = result.map { it * 10 }
        assertEquals(DomainResult.Success(20), mapped)
    }

    @Test
    fun `map leaves Error untouched`() {
        val error = DomainError.Timeout()
        val result: DomainResult<Int> = DomainResult.Error(error)
        val mapped = result.map { it * 10 }
        assertEquals(DomainResult.Error(error), mapped)
    }

    @Test
    fun `onSuccess runs action only for Success`() {
        var invoked = false
        val result: DomainResult<Int> = DomainResult.Success(1)
        result.onSuccess { invoked = true }
        assertTrue(invoked)
    }

    @Test
    fun `onSuccess does not run for Error`() {
        var invoked = false
        val result: DomainResult<Int> = DomainResult.Error(DomainError.Unknown())
        result.onSuccess { invoked = true }
        assertFalse(invoked)
    }

    @Test
    fun `onError runs action only for Error`() {
        var captured: DomainError? = null
        val error = DomainError.Conflict()
        val result: DomainResult<Int> = DomainResult.Error(error)
        result.onError { captured = it }
        assertEquals(error, captured)
    }

    @Test
    fun `onError does not run for Success`() {
        var invoked = false
        val result: DomainResult<Int> = DomainResult.Success(1)
        result.onError { invoked = true }
        assertFalse(invoked)
    }
}

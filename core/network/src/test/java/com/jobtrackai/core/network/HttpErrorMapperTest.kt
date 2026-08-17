package com.jobtrackai.core.network

import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HttpErrorMapperTest {

    @Test
    fun `maps 401 to Unauthorized`() {
        assertTrue(HttpErrorMapper.mapHttpCode(401) is DomainError.Unauthorized)
    }

    @Test
    fun `maps 403 to Forbidden`() {
        assertTrue(HttpErrorMapper.mapHttpCode(403) is DomainError.Forbidden)
    }

    @Test
    fun `maps 404 to NotFound`() {
        assertTrue(HttpErrorMapper.mapHttpCode(404) is DomainError.NotFound)
    }

    @Test
    fun `maps 409 to Conflict`() {
        assertTrue(HttpErrorMapper.mapHttpCode(409) is DomainError.Conflict)
    }

    @Test
    fun `maps 429 to RateLimited`() {
        assertTrue(HttpErrorMapper.mapHttpCode(429) is DomainError.RateLimited)
    }

    @Test
    fun `maps 500 and above to ServerError with the original code preserved`() {
        val error = HttpErrorMapper.mapHttpCode(503)
        assertTrue(error is DomainError.ServerError)
        assertEquals(503, (error as DomainError.ServerError).httpCode)
    }

    @Test
    fun `maps unrecognized codes to Unknown`() {
        assertTrue(HttpErrorMapper.mapHttpCode(200) is DomainError.Unknown)
    }

    @Test
    fun `maps UnknownHostException to NetworkUnavailable`() {
        assertTrue(HttpErrorMapper.mapThrowable(UnknownHostException()) is DomainError.NetworkUnavailable)
    }

    @Test
    fun `maps SocketTimeoutException to Timeout`() {
        assertTrue(HttpErrorMapper.mapThrowable(SocketTimeoutException()) is DomainError.Timeout)
    }

    @Test
    fun `maps generic IOException to NetworkUnavailable`() {
        assertTrue(HttpErrorMapper.mapThrowable(IOException()) is DomainError.NetworkUnavailable)
    }

    @Test
    fun `maps JobTrackApiException with http code to the matching mapped error`() {
        val exception = JobTrackApiException(httpCode = 404, message = "not found")
        assertTrue(HttpErrorMapper.mapThrowable(exception) is DomainError.NotFound)
    }

    @Test
    fun `maps JobTrackApiException without http code to Unknown`() {
        val exception = JobTrackApiException(httpCode = null, message = "unparseable body")
        assertTrue(HttpErrorMapper.mapThrowable(exception) is DomainError.Unknown)
    }

    @Test
    fun `maps unrecognized throwable to Unknown`() {
        assertTrue(HttpErrorMapper.mapThrowable(IllegalStateException("boom")) is DomainError.Unknown)
    }

    @Test
    fun `toDomainResult converts ApiResult Success to DomainResult Success`() {
        val apiResult = ApiResult.Success(data = "payload", httpCode = 200)
        val domainResult = HttpErrorMapper.toDomainResult(apiResult)
        assertTrue(domainResult is DomainResult.Success)
        assertEquals("payload", (domainResult as DomainResult.Success).data)
    }

    @Test
    fun `toDomainResult prefers the thrown cause over the raw http code when both are present`() {
        val apiResult = ApiResult.Failure(
            httpCode = 500,
            rawBody = null,
            cause = UnknownHostException(),
        )
        val domainResult = HttpErrorMapper.toDomainResult(apiResult)
        assertTrue(domainResult is DomainResult.Error)
        assertTrue((domainResult as DomainResult.Error).error is DomainError.NetworkUnavailable)
    }

    @Test
    fun `toDomainResult falls back to http code when there is no cause`() {
        val apiResult = ApiResult.Failure(httpCode = 404, rawBody = "missing", cause = null)
        val domainResult = HttpErrorMapper.toDomainResult(apiResult)
        assertTrue(domainResult is DomainResult.Error)
        assertTrue((domainResult as DomainResult.Error).error is DomainError.NotFound)
    }

    @Test
    fun `toDomainResult falls back to Unknown when neither cause nor http code is present`() {
        val apiResult = ApiResult.Failure(httpCode = null, rawBody = "mystery", cause = null)
        val domainResult = HttpErrorMapper.toDomainResult(apiResult)
        assertTrue(domainResult is DomainResult.Error)
        assertTrue((domainResult as DomainResult.Error).error is DomainError.Unknown)
    }
}

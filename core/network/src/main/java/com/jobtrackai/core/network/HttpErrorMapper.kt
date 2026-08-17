package com.jobtrackai.core.network

import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * The one place technical network failures become [DomainError]s (Section
 * 26). Every Repository routes its OkHttp calls through this so a
 * ViewModel never has to know the difference between "no internet" and
 * "DNS resolution failed" — both just become [DomainError.NetworkUnavailable].
 */
object HttpErrorMapper {

    /** Maps a completed HTTP response's status code to the matching [DomainError]. */
    fun mapHttpCode(code: Int, rawBody: String? = null): DomainError = when (code) {
        401 -> DomainError.Unauthorized(debugMessage = "HTTP 401: $rawBody")
        403 -> DomainError.Forbidden(debugMessage = "HTTP 403: $rawBody")
        404 -> DomainError.NotFound(debugMessage = "HTTP 404: $rawBody")
        409 -> DomainError.Conflict(debugMessage = "HTTP 409: $rawBody")
        429 -> DomainError.RateLimited(debugMessage = "HTTP 429: $rawBody")
        in 500..599 -> DomainError.ServerError(httpCode = code, debugMessage = "HTTP $code: $rawBody")
        else -> DomainError.Unknown(debugMessage = "Unexpected HTTP $code: $rawBody")
    }

    /** Maps a thrown exception (call never completed) to the matching [DomainError]. */
    fun mapThrowable(throwable: Throwable): DomainError = when (throwable) {
        is UnknownHostException -> DomainError.NetworkUnavailable(debugMessage = throwable.message)
        is SocketTimeoutException -> DomainError.Timeout(debugMessage = throwable.message)
        is JobTrackApiException -> throwable.httpCode
            ?.let { mapHttpCode(it, throwable.message) }
            ?: DomainError.Unknown(debugMessage = throwable.message)
        is IOException -> DomainError.NetworkUnavailable(debugMessage = throwable.message)
        else -> DomainError.Unknown(debugMessage = throwable.message)
    }

    /** Converts a fully-resolved [ApiResult] into the [DomainResult] a Repository returns upward. */
    fun <T> toDomainResult(apiResult: ApiResult<T>): DomainResult<T> = when (apiResult) {
        is ApiResult.Success -> DomainResult.Success(apiResult.data)
        is ApiResult.Failure -> DomainResult.Error(
            apiResult.cause?.let(::mapThrowable)
                ?: apiResult.httpCode?.let { mapHttpCode(it, apiResult.rawBody) }
                ?: DomainError.Unknown(debugMessage = apiResult.rawBody),
        )
    }
}

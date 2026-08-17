package com.jobtrackai.core.network

/**
 * Thrown internally within `core:network` when an HTTP response can't be
 * parsed into the expected type, or the server returns a non-2xx status
 * that the caller didn't handle via [ApiResult.Failure].
 *
 * This should never leak past `core:network` — [HttpErrorMapper] catches it
 * (along with `IOException`, `SocketTimeoutException`, etc.) and converts it
 * to a [com.jobtrackai.core.common.result.DomainError] before a Repository
 * ever sees it.
 */
class JobTrackApiException(
    val httpCode: Int?,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

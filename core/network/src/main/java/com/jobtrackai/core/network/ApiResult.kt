package com.jobtrackai.core.network

/**
 * Raw outcome of a single HTTP call, before a Repository maps it into a
 * [com.jobtrackai.core.common.result.DomainResult].
 *
 * Kept separate from `DomainResult` on purpose: `ApiResult` is allowed to
 * carry network-specific detail (status code, raw body) that's useful for
 * logging/Crashlytics breadcrumbs at the network boundary, but which a
 * ViewModel three layers up should never see. Repositories are the only
 * thing that touches `ApiResult` — they translate it to `DomainResult`
 * (via [HttpErrorMapper]) before anything else in the app sees it.
 */
sealed interface ApiResult<out T> {

    data class Success<out T>(val data: T, val httpCode: Int) : ApiResult<T>

    data class Failure(
        val httpCode: Int?,
        val rawBody: String?,
        val cause: Throwable?,
    ) : ApiResult<Nothing>
}

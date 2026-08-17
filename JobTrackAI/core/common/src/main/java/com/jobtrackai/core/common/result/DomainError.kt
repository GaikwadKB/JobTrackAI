package com.jobtrackai.core.common.result

/**
 * The complete set of failure reasons any Repository can surface to a
 * UseCase/ViewModel.
 *
 * This exists so the app never leans on a raw exception (`java.net.
 * UnknownHostException`, `SQLiteException`, etc.) past the data layer.
 * `core:network`'s `HttpErrorMapper` and `core:database`'s DAO wrappers are
 * the only places that translate technical exceptions into these — everyone
 * else pattern-matches on this closed hierarchy. This is what lets Rule 35
 * ("Instead of 'java.net.UnknownHostException' show 'No internet
 * connection...'") be enforced in exactly one place (the ViewModel's
 * error-to-message mapping) rather than scattered across every screen.
 *
 * Kept in `core:common` (no Android/network/database dependency) so any
 * module — including pure Kotlin unit tests — can reference it without
 * pulling in OkHttp or Room.
 */
sealed class DomainError(
    /** Non-localized, developer-facing detail for logs/Crashlytics breadcrumbs (Section 27: never log secrets, but the error *kind* and safe context are fine). */
    val debugMessage: String? = null,
) {

    /** No network connectivity, or the request never reached the server. */
    class NetworkUnavailable(debugMessage: String? = null) : DomainError(debugMessage)

    /** 401 — missing/expired/invalid auth token. Callers typically route this to a re-login flow. */
    class Unauthorized(debugMessage: String? = null) : DomainError(debugMessage)

    /** 403 — authenticated but not permitted. Distinct from [Unauthorized] so the UI can message it differently. */
    class Forbidden(debugMessage: String? = null) : DomainError(debugMessage)

    /** 404 — requested resource doesn't exist server-side. */
    class NotFound(debugMessage: String? = null) : DomainError(debugMessage)

    /** 409 — e.g. duplicate application/interview (Section 48 test scenario). */
    class Conflict(debugMessage: String? = null) : DomainError(debugMessage)

    /** 429 — client should back off; paired with Section 25's exponential-backoff retry. */
    class RateLimited(debugMessage: String? = null) : DomainError(debugMessage)

    /** 500+ — server-side failure, not the client's fault. */
    class ServerError(val httpCode: Int? = null, debugMessage: String? = null) : DomainError(debugMessage)

    /** Request exceeded its deadline — distinct from [NetworkUnavailable] because retry strategy differs (timeout may succeed on retry; no connectivity won't). */
    class Timeout(debugMessage: String? = null) : DomainError(debugMessage)

    /** Local persistence failure (Room/DataStore) — Section 48 "Database failure" scenario. */
    class DatabaseError(debugMessage: String? = null) : DomainError(debugMessage)

    /** Field-level input validation failure (Section 46). Carries the offending field so the UI can highlight it. */
    class ValidationFailed(val field: String, debugMessage: String? = null) : DomainError(debugMessage)

    /** Everything else — kept as a single catch-all rather than one class per obscure exception type, so this hierarchy stays a small, exhaustive `when`. */
    class Unknown(debugMessage: String? = null) : DomainError(debugMessage)
}

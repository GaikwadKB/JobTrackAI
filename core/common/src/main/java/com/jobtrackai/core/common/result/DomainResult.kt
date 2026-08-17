package com.jobtrackai.core.common.result

/**
 * The single result type returned by every UseCase and Repository in the app.
 *
 * Deliberately not Kotlin's stdlib `Result<T>`: that type can't carry a typed
 * error hierarchy (see [DomainError]), doesn't compose cleanly with `Flow`,
 * and encourages catching `Throwable` at call sites instead of mapping
 * failures once, at the boundary where they occur (network client, DAO,
 * etc.). Every layer above that boundary works with [DomainResult] only.
 *
 * UI layers (Section 31 — ViewModel UiState) pattern-match on this to derive
 * their Loading/Success/Empty/Error/Offline state, per Rule 7.
 */
sealed interface DomainResult<out T> {

    data class Success<out T>(val data: T) : DomainResult<T>

    data class Error(val error: DomainError) : DomainResult<Nothing>

    /**
     * True when this result represents success. Kept as a property (not a
     * function) so call sites read naturally: `if (result.isSuccess) { ... }`.
     */
    val isSuccess: Boolean
        get() = this is Success<*>

    val isError: Boolean
        get() = this is Error
}

/**
 * Returns the success value, or `null` if this is an [DomainResult.Error].
 * Convenience for call sites that want to fall back rather than branch.
 */
fun <T> DomainResult<T>.getOrNull(): T? =
    (this as? DomainResult.Success<T>)?.data

/**
 * Transforms the success value in place, leaving [DomainResult.Error]
 * results untouched. Mirrors `Result.map` from the stdlib for familiarity.
 */
inline fun <T, R> DomainResult<T>.map(transform: (T) -> R): DomainResult<R> =
    when (this) {
        is DomainResult.Success -> DomainResult.Success(transform(data))
        is DomainResult.Error -> this
    }

/**
 * Runs [action] only when this is a [DomainResult.Success], then returns the
 * original result unchanged — useful for side effects (logging, caching)
 * without breaking a call chain.
 */
inline fun <T> DomainResult<T>.onSuccess(action: (T) -> Unit): DomainResult<T> {
    if (this is DomainResult.Success) action(data)
    return this
}

/**
 * Runs [action] only when this is a [DomainResult.Error], then returns the
 * original result unchanged.
 */
inline fun <T> DomainResult<T>.onError(action: (DomainError) -> Unit): DomainResult<T> {
    if (this is DomainResult.Error) action(error)
    return this
}

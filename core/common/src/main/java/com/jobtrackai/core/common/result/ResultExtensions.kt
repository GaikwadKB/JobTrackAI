package com.jobtrackai.core.common.result

import com.jobtrackai.core.common.ui.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Maps a [DomainResult] to its corresponding [UiState].
 *
 * @param isEmpty A lambda that determines if a success result should be
 *   treated as [UiState.Empty] (e.g., an empty list).
 */
fun <T> DomainResult<T>.toUiState(
    isEmpty: (T) -> Boolean = { false }
): UiState<T> = when (this) {
    is DomainResult.Success -> {
        if (isEmpty(data)) UiState.Empty else UiState.Success(data)
    }
    is DomainResult.Error -> UiState.Error(error)
}

/**
 * Transforms a [Flow] of [DomainResult] into a [Flow] of [UiState].
 *
 * Automatically handles the [UiState.Loading] state at the start of the flow
 * and catches any unhandled exceptions as [DomainError.Unknown].
 */
fun <T> Flow<DomainResult<T>>.asUiState(
    isEmpty: (T) -> Boolean = { false }
): Flow<UiState<T>> = this
    .map { it.toUiState(isEmpty) }
    .onStart { emit(UiState.Loading) }
    .catch { emit(UiState.Error(DomainError.Unknown(it.message))) }

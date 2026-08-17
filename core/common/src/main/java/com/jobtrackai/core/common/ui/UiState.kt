package com.jobtrackai.core.common.ui

import com.jobtrackai.core.common.result.DomainError

/**
 * Standard UI state wrapper for the whole app (Section 31).
 *
 * This enforces Rule 7: every screen must handle Loading, Success, Empty,
 * and Error states. By using a sealed interface, we ensure that Composable
 * functions can exhaustively switch over these states, preventing UI bugs
 * where a loading spinner hangs or an error isn't shown.
 */
sealed interface UiState<out T> {

    /** The initial state before any data has been requested. */
    data object Idle : UiState<Nothing>

    /** Data is being fetched from network or database. */
    data object Loading : UiState<Nothing>

    /** Data was successfully fetched and is not empty. */
    data class Success<out T>(val data: T) : UiState<T>

    /** Data was successfully fetched but there are no items to show (Rule 37). */
    data object Empty : UiState<Nothing>

    /** Something went wrong (Rule 35). */
    data class Error(val error: DomainError) : UiState<Nothing>
}

package com.jobtrackai.core.common.model

/**
 * Domain model for an authenticated user.
 *
 * This is the pure business representation of a user, decoupled from
 * Firebase's `FirebaseUser` or Room's `UserEntity`.
 */
data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val photoUrl: String? = null
)

package com.jobtrackai.core.common.model

/**
 * Domain model for the user's professional profile.
 */
data class UserProfile(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val currentRole: String = "",
    val experienceYears: Int = 0,
    val skills: List<String> = emptyList(),
    val education: String = "",
    val expectedSalary: Double = 0.0,
    val preferredLocation: String = "",
    val remotePreference: RemotePreference = RemotePreference.REMOTE,
    val photoUrl: String? = null
)

enum class RemotePreference {
    REMOTE,
    ONSITE,
    HYBRID
}

package com.jobtrackai.feature.profile.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.model.UserProfile
import com.jobtrackai.core.common.result.DomainError
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.network.HttpErrorMapper
import com.jobtrackai.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : ProfileRepository {

    private val profilesCollection = firestore.collection("profiles")

    override fun getProfile(userId: String): Flow<DomainResult<UserProfile?>> = callbackFlow {
        val subscription = profilesCollection.document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(DomainResult.Error(HttpErrorMapper.mapThrowable(error)))
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val profile = snapshot.toObject(ProfileDto::class.java)?.toDomain(userId)
                    trySend(DomainResult.Success(profile))
                } else {
                    trySend(DomainResult.Success(null))
                }
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun updateProfile(profile: UserProfile): DomainResult<Unit> = try {
        profilesCollection.document(profile.userId)
            .set(profile.toDto(), SetOptions.merge())
            .await()
        DomainResult.Success(Unit)
    } catch (e: Exception) {
        DomainResult.Error(HttpErrorMapper.mapThrowable(e))
    }
}

/**
 * Data Transfer Object for Firestore.
 */
internal data class ProfileDto(
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
    val remotePreference: String = "REMOTE",
    val photoUrl: String? = null
) {
    fun toDomain(userId: String): UserProfile = UserProfile(
        userId = userId,
        name = name,
        email = email,
        phone = phone,
        location = location,
        currentRole = currentRole,
        experienceYears = experienceYears,
        skills = skills,
        education = education,
        expectedSalary = expectedSalary,
        preferredLocation = preferredLocation,
        remotePreference = try { RemotePreference.valueOf(remotePreference) } catch (e: Exception) { RemotePreference.REMOTE },
        photoUrl = photoUrl
    )
}

internal fun UserProfile.toDto(): ProfileDto = ProfileDto(
    name = name,
    email = email,
    phone = phone,
    location = location,
    currentRole = currentRole,
    experienceYears = experienceYears,
    skills = skills,
    education = education,
    expectedSalary = expectedSalary,
    preferredLocation = preferredLocation,
    remotePreference = remotePreference.name,
    photoUrl = photoUrl
)

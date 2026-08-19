package com.jobtrackai.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.model.UserProfile

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val email: String,
    val phone: String,
    val location: String,
    val currentRole: String,
    val experienceYears: Int,
    val skills: List<String>,
    val education: String,
    val expectedSalary: Double,
    val preferredLocation: String,
    val remotePreference: RemotePreference,
    val photoUrl: String?
)

fun ProfileEntity.toDomain(): UserProfile = UserProfile(
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
    remotePreference = remotePreference,
    photoUrl = photoUrl
)

fun UserProfile.toEntity(): ProfileEntity = ProfileEntity(
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
    remotePreference = remotePreference,
    photoUrl = photoUrl
)

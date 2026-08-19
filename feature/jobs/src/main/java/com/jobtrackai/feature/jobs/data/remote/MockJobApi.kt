package com.jobtrackai.feature.jobs.data.remote

import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.feature.jobs.domain.model.Job
import kotlinx.coroutines.delay
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock remote data source for jobs (Rule 64).
 */
@Singleton
class MockJobApi @Inject constructor() {

    private val baseJobs = listOf(
        Job(
            id = "1",
            title = "Senior Android Engineer",
            companyName = "TechCorp Solutions",
            location = "Bangalore, India",
            jobType = "Full-time",
            workMode = RemotePreference.HYBRID,
            salaryMin = 1500000.0,
            salaryMax = 2500000.0,
            description = "Lead the development of our flagship Android application...",
            requirements = "5+ years experience, Kotlin, Jetpack Compose",
            skills = listOf("Kotlin", "Compose", "Hilt", "Coroutines"),
            experienceRequired = "5+ years",
            source = "LinkedIn",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "2",
            title = "Kotlin Developer",
            companyName = "InnovateFlow",
            location = "Remote",
            jobType = "Contract",
            workMode = RemotePreference.REMOTE,
            salaryMin = 1000000.0,
            salaryMax = 1800000.0,
            description = "We are looking for a Kotlin enthusiast to join our team...",
            requirements = "Strong understanding of Kotlin and Coroutines",
            skills = listOf("Kotlin", "Flow", "SQL"),
            experienceRequired = "2-4 years",
            source = "Glassdoor",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "3",
            title = "Both Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "4",
            title = "Junior Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "5",
            title = "Io Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "6",
            title = "Junior Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "7",
            title = "New Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "8",
            title = "Ex Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Job(
            id = "9",
            title = "s. Mobile Developer",
            companyName = "StartUp Inc",
            location = "Mumbai, India",
            jobType = "Full-time",
            workMode = RemotePreference.ONSITE,
            salaryMin = 600000.0,
            salaryMax = 900000.0,
            description = "Learn and grow with our mobile development team...",
            requirements = "Degree in Computer Science or equivalent experience",
            skills = listOf("Java", "Android SDK"),
            experienceRequired = "0-1 year",
            source = "Indeed",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

    )

    suspend fun searchJobs(query: String, page: Int): List<Job> {
        delay(1000) // Simulate network delay
        
        // Return filtered base jobs or generate some more based on page
        return if (query.isBlank()) {
            baseJobs
        } else {
            baseJobs.filter { it.title.contains(query, ignoreCase = true) || it.companyName.contains(query, ignoreCase = true) }
        }
    }
}

package com.jobtrackai.core.common.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation destinations for the app.
 *
 * Using Kotlin Serialization for routes (modern standard for Compose Navigation).
 */
sealed interface NavDestinations {

    /**
     * Auth graph (Phase 5).
     */
    @Serializable
    data object AuthGraph : NavDestinations

    @Serializable
    data object Login : NavDestinations

    @Serializable
    data object Register : NavDestinations

    @Serializable
    data object ForgotPassword : NavDestinations

    /**
     * Main app graph (post-auth).
     */
    @Serializable
    data object MainGraph : NavDestinations

    @Serializable
    data object Home : NavDestinations

    @Serializable
    data object Jobs : NavDestinations

    @Serializable
    data object Applications : NavDestinations

    @Serializable
    data object Interviews : NavDestinations

    @Serializable
    data object Profile : NavDestinations

    /**
     * Feature sub-screens.
     */
    @Serializable
    data class JobDetails(val jobId: String) : NavDestinations

    @Serializable
    data class ApplicationDetails(val applicationId: String) : NavDestinations

    @Serializable
    data class InterviewDetails(val interviewId: String) : NavDestinations
}

package com.jobtrackai.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.analytics.navigation.homeScreen
import com.jobtrackai.feature.applications.navigation.applicationsScreen
import com.jobtrackai.feature.applications.navigation.navigateToApplicationDetails
import com.jobtrackai.feature.applications.navigation.navigateToApplications
import com.jobtrackai.feature.applications.presentation.tracker.ApplicationTrackerViewModel
import com.jobtrackai.feature.auth.navigation.authGraph
import com.jobtrackai.feature.interviews.navigation.interviewsScreen
import com.jobtrackai.feature.interviews.navigation.navigateToAddInterview
import com.jobtrackai.feature.jobs.navigation.jobsScreen
import com.jobtrackai.feature.jobs.navigation.navigateToJobDetails
import com.jobtrackai.feature.profile.navigation.profileScreen

/**
 * Root navigation host for the app.
 *
 * Coordinates transitions between [NavDestinations.AuthGraph] and
 * [NavDestinations.MainGraph].
 */
@Composable
fun JobTrackNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = NavDestinations.AuthGraph
) {
    val applicationsViewModel: ApplicationTrackerViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authGraph(
            onLoginSuccess = {
                navController.navigate(NavDestinations.Home) {
                    popUpTo(NavDestinations.AuthGraph) { inclusive = true }
                }
            },
            navController = navController
        )
        
        homeScreen()
        jobsScreen(
            onJobClick = { jobId -> navController.navigateToJobDetails(jobId) },
            onBackClick = { navController.popBackStack() },
            onApplyClick = { job ->
                applicationsViewModel.applyToJob(job)
                navController.navigateToApplications()
            }
        )
        applicationsScreen(
            onApplicationClick = { appId -> navController.navigateToApplicationDetails(appId) },
            onBackClick = { navController.popBackStack() },
            onScheduleInterviewClick = { appId -> navController.navigateToAddInterview(appId) }
        )
        interviewsScreen(
            onBackClick = { navController.popBackStack() }
        )
        profileScreen()
    }
}

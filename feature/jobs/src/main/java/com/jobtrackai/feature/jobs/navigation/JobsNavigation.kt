package com.jobtrackai.feature.jobs.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.jobs.domain.model.Job
import com.jobtrackai.feature.jobs.presentation.details.JobDetailsRoute
import com.jobtrackai.feature.jobs.search.JobSearchRoute

fun NavController.navigateToJobs(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Jobs, navOptions)
}

fun NavController.navigateToJobDetails(jobId: String, navOptions: NavOptions? = null) {
    navigate(NavDestinations.JobDetails(jobId), navOptions)
}

fun NavGraphBuilder.jobsScreen(
    onJobClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onApplyClick: (Job) -> Unit
) {
    composable<NavDestinations.Jobs> {
        JobSearchRoute(onJobClick = onJobClick)
    }

    composable<NavDestinations.JobDetails> { backStackEntry ->
        val details = backStackEntry.toRoute<NavDestinations.JobDetails>()
        JobDetailsRoute(
            jobId = details.jobId,
            onBackClick = onBackClick,
            onApplyClick = onApplyClick
        )
    }
}

package com.jobtrackai.feature.jobs.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.jobs.search.JobSearchRoute

fun NavController.navigateToJobs(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Jobs, navOptions)
}

fun NavGraphBuilder.jobsScreen() {
    composable<NavDestinations.Jobs> {
        JobSearchRoute()
    }
}

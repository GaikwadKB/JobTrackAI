package com.jobtrackai.feature.applications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.applications.tracker.ApplicationsRoute

fun NavController.navigateToApplications(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Applications, navOptions)
}

fun NavGraphBuilder.applicationsScreen() {
    composable<NavDestinations.Applications> {
        ApplicationsRoute()
    }
}

package com.jobtrackai.feature.applications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.applications.presentation.details.ApplicationDetailsRoute
import com.jobtrackai.feature.applications.tracker.ApplicationsRoute

fun NavController.navigateToApplications(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Applications, navOptions)
}

fun NavController.navigateToApplicationDetails(applicationId: String, navOptions: NavOptions? = null) {
    navigate(NavDestinations.ApplicationDetails(applicationId), navOptions)
}

fun NavGraphBuilder.applicationsScreen(
    onApplicationClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable<NavDestinations.Applications> {
        ApplicationsRoute(onApplicationClick = onApplicationClick)
    }

    composable<NavDestinations.ApplicationDetails> { backStackEntry ->
        val details = backStackEntry.toRoute<NavDestinations.ApplicationDetails>()
        ApplicationDetailsRoute(
            applicationId = details.applicationId,
            onBackClick = onBackClick
        )
    }
}

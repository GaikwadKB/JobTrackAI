package com.jobtrackai.feature.interviews.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.interviews.list.InterviewsRoute
import com.jobtrackai.feature.interviews.presentation.add.AddInterviewRoute

fun NavController.navigateToInterviews(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Interviews, navOptions)
}

fun NavController.navigateToAddInterview(applicationId: String, navOptions: NavOptions? = null) {
    navigate(NavDestinations.AddInterview(applicationId), navOptions)
}

fun NavGraphBuilder.interviewsScreen(
    onBackClick: () -> Unit
) {
    composable<NavDestinations.Interviews> {
        InterviewsRoute()
    }

    composable<NavDestinations.AddInterview> { backStackEntry ->
        val dest = backStackEntry.toRoute<NavDestinations.AddInterview>()
        AddInterviewRoute(
            applicationId = dest.applicationId,
            onBackClick = onBackClick,
            onSuccess = onBackClick
        )
    }
}

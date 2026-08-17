package com.jobtrackai.feature.interviews.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.interviews.list.InterviewsRoute

fun NavController.navigateToInterviews(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Interviews, navOptions)
}

fun NavGraphBuilder.interviewsScreen() {
    composable<NavDestinations.Interviews> {
        InterviewsRoute()
    }
}

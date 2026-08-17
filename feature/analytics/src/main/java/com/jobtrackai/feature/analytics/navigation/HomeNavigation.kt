package com.jobtrackai.feature.analytics.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.analytics.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Home, navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable<NavDestinations.Home> {
        HomeRoute()
    }
}

package com.jobtrackai.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.profile.details.ProfileRoute

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Profile, navOptions)
}

fun NavGraphBuilder.profileScreen() {
    composable<NavDestinations.Profile> {
        ProfileRoute()
    }
}

package com.jobtrackai.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.auth.login.LoginRoute

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(NavDestinations.AuthGraph, navOptions)
}

fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit
) {
    navigation<NavDestinations.AuthGraph>(
        startDestination = NavDestinations.Login
    ) {
        composable<NavDestinations.Login> {
            LoginRoute(onLoginSuccess = onLoginSuccess)
        }
        
        // Register and ForgotPassword will be added in Phase 5
    }
}

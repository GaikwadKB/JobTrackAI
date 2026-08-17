package com.jobtrackai.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.auth.forgotpassword.ForgotPasswordRoute
import com.jobtrackai.feature.auth.login.LoginRoute
import com.jobtrackai.feature.auth.register.RegisterRoute

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(NavDestinations.AuthGraph, navOptions)
}

fun NavController.navigateToRegister(navOptions: NavOptions? = null) {
    navigate(NavDestinations.Register, navOptions)
}

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) {
    navigate(NavDestinations.ForgotPassword, navOptions)
}

fun NavGraphBuilder.authGraph(
    onLoginSuccess: () -> Unit,
    navController: NavController
) {
    navigation<NavDestinations.AuthGraph>(
        startDestination = NavDestinations.Login
    ) {
        composable<NavDestinations.Login> {
            LoginRoute(
                onLoginSuccess = onLoginSuccess,
                onRegisterClick = { navController.navigateToRegister() },
                onForgotPasswordClick = { navController.navigateToForgotPassword() }
            )
        }
        
        composable<NavDestinations.Register> {
            RegisterRoute(
                onRegisterSuccess = onLoginSuccess,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<NavDestinations.ForgotPassword> {
            ForgotPasswordRoute(
                onResetSent = { navController.popBackStack() },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

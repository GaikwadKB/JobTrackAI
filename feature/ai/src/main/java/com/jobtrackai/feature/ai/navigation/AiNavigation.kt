package com.jobtrackai.feature.ai.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.feature.ai.presentation.AIInterviewResultRoute
import com.jobtrackai.feature.ai.presentation.AIInterviewSetupScreen
import com.jobtrackai.feature.ai.presentation.AIMockInterviewRoute

fun NavController.navigateToAIInterviewSetup(navOptions: NavOptions? = null) {
    navigate(NavDestinations.AIInterviewSetup, navOptions)
}

fun NavController.navigateToAIMockInterview(role: String, level: String, count: Int, navOptions: NavOptions? = null) {
    navigate(NavDestinations.AIMockInterview(role, level, count), navOptions)
}

fun NavController.navigateToAIInterviewResult(sessionId: String, navOptions: NavOptions? = null) {
    navigate(NavDestinations.AIInterviewResult(sessionId), navOptions)
}

fun NavGraphBuilder.aiGraph(
    navController: NavController
) {
    composable<NavDestinations.AIInterviewSetup> {
        AIInterviewSetupScreen(
            onStartInterview = { role, level, count ->
                navController.navigateToAIMockInterview(role, level, count)
            }
        )
    }

    composable<NavDestinations.AIMockInterview> { backStackEntry ->
        val dest = backStackEntry.toRoute<NavDestinations.AIMockInterview>()
        AIMockInterviewRoute(
            role = dest.role,
            level = dest.level,
            count = dest.count,
            onFinish = { sessionId ->
                navController.navigateToAIInterviewResult(sessionId, navOptions {
                    popUpTo(NavDestinations.AIInterviewSetup) { inclusive = false }
                })
            }
        )
    }

    composable<NavDestinations.AIInterviewResult> { backStackEntry ->
        val dest = backStackEntry.toRoute<NavDestinations.AIInterviewResult>()
        AIInterviewResultRoute(
            sessionId = dest.sessionId,
            onBackClick = { navController.popBackStack() }
        )
    }
}

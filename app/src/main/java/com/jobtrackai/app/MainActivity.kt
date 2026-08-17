package com.jobtrackai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jobtrackai.app.navigation.JobTrackNavHost
import com.jobtrackai.app.presentation.MainUiState
import com.jobtrackai.app.presentation.MainViewModel
import com.jobtrackai.core.common.navigation.NavDestinations
import com.jobtrackai.core.designsystem.component.JobTrackNavigationBar
import com.jobtrackai.core.designsystem.theme.JobTrackTheme
import com.jobtrackai.core.designsystem.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity host for the whole app.
 *
 * Hosting Phase 5 Authentication and Navigation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobTrackTheme {
                MainApp()
            }
        }
    }
}

@Composable
private fun MainApp(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (uiState is MainUiState.Loading) {
        // You could show a splash screen here
        return
    }

    val startDestination = if (uiState is MainUiState.Authenticated) {
        NavDestinations.Home
    } else {
        NavDestinations.AuthGraph
    }

    // Only show navigation bar for top-level MainGraph destinations
    val showBottomBar = currentDestination?.hierarchy?.any {
        it.hasRoute(NavDestinations.Home::class) ||
            it.hasRoute(NavDestinations.Jobs::class) ||
            it.hasRoute(NavDestinations.Applications::class) ||
            it.hasRoute(NavDestinations.Interviews::class) ||
            it.hasRoute(NavDestinations.Profile::class)
    } == true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                JobTrackNavigationBar(
                    currentDestination = currentDestination,
                    onNavigateToDestination = { destination ->
                        navController.navigate(destination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        JobTrackNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = startDestination
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainAppPreview() {
    JobTrackTheme {
        MainApp()
    }
}

@Preview(showBackground = true, name = "Dark theme")
@Composable
private fun MainAppDarkPreview() {
    JobTrackTheme(themeMode = ThemeMode.Dark) {
        MainApp()
    }
}


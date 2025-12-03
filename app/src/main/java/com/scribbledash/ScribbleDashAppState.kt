package com.scribbledash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.scribbledash.core.presentation.navigation.TopLevelDestination
import com.scribbledash.core.presentation.navigation.TopLevelDestination.*
import com.scribbledash.home.navigation.navigateToHome
import com.scribbledash.statistics.navigation.navigateToStatistic

@Composable
fun rememberScribbleDashAppState(
    navController: NavHostController = rememberNavController(),
): ScribbleDashAppState {
    return remember(navController) {
        ScribbleDashAppState(navController)
    }
}

class ScribbleDashAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                HOME -> navController.navigateToHome(topLevelNavOptions)
                STATISTIC -> navController.navigateToStatistic(topLevelNavOptions)
                SHOP -> {}
            }
        }
    }
}

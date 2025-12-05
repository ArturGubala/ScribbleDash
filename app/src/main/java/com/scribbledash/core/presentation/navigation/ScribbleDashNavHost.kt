package com.scribbledash.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.scribbledash.ScribbleDashAppState
import com.scribbledash.core.presentation.screens.difficultylevel.navigation.difficultyLevelScreen
import com.scribbledash.gameplay.navigation.gameplayNavGraph
import com.scribbledash.home.navigation.HomeScreen
import com.scribbledash.home.navigation.homeScreen
import com.scribbledash.shop.navigation.shopScreen
import com.scribbledash.statistics.navigation.statisticScreen

@Composable
fun ScribbleDashNavHost(
    appState: ScribbleDashAppState,
    modifier: Modifier = Modifier,
    startDestination: Any = HomeScreen
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(navController = navController)
        statisticScreen(onBackClick = { navController.popBackStack() })
        shopScreen(navController = navController)
        difficultyLevelScreen(navController = navController)
        gameplayNavGraph(navController)
    }
}

package com.scribbledash.gameplay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.gameplay.presentation.screens.GameplayRoute
import com.scribbledash.gameplay.presentation.screens.ResultRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.gameplayNavGraph(navController: NavController) {
    navigation(
        startDestination = GameplayScreen.serializer().toString(),
        route = "gameplay_root"
    ) {
        gameplayScreen(navController)
        resultScreen(navController)
    }
}

fun NavController.navigateToGameplay(navOptions: NavOptions? = null) =
    navigate(GameplayScreen, navOptions = navOptions)

fun NavGraphBuilder.gameplayScreen(
    navController: NavController
) {
    composable<GameplayScreen> {
        GameplayRoute(navController = navController)
    }
}

fun NavController.navigateToResult(navOptions: NavOptions? = null) =
    navigate(ResultScreen, navOptions = navOptions)

fun NavGraphBuilder.resultScreen(
    navController: NavController
) {
    composable<ResultScreen> {
        ResultRoute(navController = navController)
    }
}

@Serializable
object GameplayScreen : Route

@Serializable
object ResultScreen : Route

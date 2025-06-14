package com.scribbledash.gameplay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.gameplay.GameplayRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToGameplay(navOptions: NavOptions? = null) =
    navigate(GameplayScreen, navOptions = navOptions)

fun NavGraphBuilder.gameplayScreen(
    navController: NavController
) {
    composable<GameplayScreen> {
        GameplayRoute(navController = navController)
    }
}

@Serializable
object GameplayScreen : Route

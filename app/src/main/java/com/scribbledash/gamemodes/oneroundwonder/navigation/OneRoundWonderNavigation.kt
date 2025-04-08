package com.scribbledash.gamemodes.oneroundwonder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.gamemodes.oneroundwonder.OneRoundWonderRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToOneRoundWonder(navOptions: NavOptions? = null) =
    navigate(OneRoundWonderScreen, navOptions = navOptions)

fun NavGraphBuilder.oneRoundWonderScreen(
    navController: NavController
) {
    composable<OneRoundWonderScreen> {
        OneRoundWonderRoute(navController = navController)
    }
}

@Serializable
object OneRoundWonderScreen : Route

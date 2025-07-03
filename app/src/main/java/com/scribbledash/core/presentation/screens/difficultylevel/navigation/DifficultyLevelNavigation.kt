package com.scribbledash.core.presentation.screens.difficultylevel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.scribbledash.core.presentation.screens.difficultylevel.DifficultyLevelRoute
import com.scribbledash.core.presentation.utils.GameType
import kotlinx.serialization.Serializable

fun NavController.navigateToDifficultyLevel(gameType: GameType, navOptions: NavOptions? = null) {
    navigate(DifficultyLevelScreen(gameType = gameType), navOptions)
}


fun NavGraphBuilder.difficultyLevelScreen(
    navController: NavController
) {
    composable<DifficultyLevelScreen> {
        val args = it.toRoute<DifficultyLevelScreen>()
        DifficultyLevelRoute(
            navController = navController,
            gameType = args.gameType
        )
    }
}

@Serializable
data class DifficultyLevelScreen(
    val gameType: GameType
)

package com.scribbledash.core.presentation.screens.difficultylevel.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.scribbledash.core.presentation.screens.difficultylevel.DifficultyLevelRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToDifficultyLevel(finalDestination: String, navOptions: NavOptions? = null) {
    navigate(DifficultyLevelScreen(finalDestination), navOptions)
}


fun NavGraphBuilder.difficultyLevelScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    composable<DifficultyLevelScreen> {
        val args = it.toRoute<DifficultyLevelScreen>()
        DifficultyLevelRoute(
            finalDestination = args.finalDestination,
            navController = navController,
            onBackClick = onBackClick
        )
    }
}

@Serializable
class DifficultyLevelScreen(
    val finalDestination: String
)

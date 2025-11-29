package com.scribbledash.gameplay.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.gameplay.presentation.screens.GameplayRoute
import com.scribbledash.gameplay.presentation.screens.ResultRoute
import com.scribbledash.gameplay.presentation.screens.SummaryRoute
import kotlinx.serialization.Serializable

const val GAMEPLAY_GRAPH_ROUTE = "gameplay_graph"

fun NavGraphBuilder.gameplayNavGraph(navController: NavController) {
    navigation(
        startDestination = GameplayScreen.serializer().toString(),
        route = GAMEPLAY_GRAPH_ROUTE
    ) {
        gameplayScreen(navController)
        resultScreen(navController)
    }
}

fun NavController.navigateToGameplay(gameType: GameType,
                                     difficultyLevel: DifficultyLevel,
                                     navOptions: NavOptions? = null) =
    navigate(GameplayScreen(
        gameType = gameType,
        difficultyLevel = difficultyLevel),
        navOptions = navOptions)

fun NavGraphBuilder.gameplayScreen(
    navController: NavController
) {
    composable<GameplayScreen> {
        val args = it.toRoute<GameplayScreen>()
        GameplayRoute(
            gameType = args.gameType,
            difficultyLevel = args.difficultyLevel,
            navController = navController
        )
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

fun NavController.navigateToSummary(navOptions: NavOptions? = null) =
    navigate(SummaryScreen, navOptions = navOptions)

fun NavGraphBuilder.summaryScreen(
    navController: NavController
) {
    composable<SummaryScreen> {
        SummaryRoute(navController = navController)
    }
}

@Serializable
data class GameplayScreen (
    val gameType: GameType,
    val difficultyLevel: DifficultyLevel
) : Route

@Serializable
object ResultScreen : Route

@Serializable
object SummaryScreen : Route

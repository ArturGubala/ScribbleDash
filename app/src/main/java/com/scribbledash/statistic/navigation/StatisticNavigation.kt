package com.scribbledash.statistic.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.statistic.StatisticRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToStatistic(navOptions: NavOptions? = null) = navigate(StatisticScreen, navOptions)

fun NavGraphBuilder.statisticScreen(
    onBackClick: () -> Unit
) {
    composable<StatisticScreen> {
        StatisticRoute(onBackClick = onBackClick)
    }
}

@Serializable
object StatisticScreen

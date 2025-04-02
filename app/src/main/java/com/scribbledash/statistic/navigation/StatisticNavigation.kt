package com.scribbledash.statistic.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToStatistic(navOptions: NavOptions? = null) = navigate(StatisticScreen, navOptions)

fun NavGraphBuilder.statisticScreen(
    onBackClick: () -> Unit
) {
    composable<StatisticScreen> {
        Text("Statistic screen")
    }
}

@Serializable
object StatisticScreen

package com.scribbledash.statistic.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.core.presentation.utils.GradientScheme
import kotlinx.serialization.Serializable

fun NavController.navigateToStatistic(navOptions: NavOptions? = null) = navigate(StatisticScreen, navOptions)

fun NavGraphBuilder.statisticScreen(
    onBackClick: () -> Unit
) {
    composable<StatisticScreen> {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier
                .background(GradientScheme.PrimaryGradient)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Statistic screen")
                Text("Under preparation")
            }
        }
    }
}

@Serializable
object StatisticScreen

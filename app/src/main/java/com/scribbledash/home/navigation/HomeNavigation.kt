package com.scribbledash.home.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HomeScreen, navOptions)

fun NavGraphBuilder.homeScreen(
    onBackClick: () -> Unit
) {
    composable<HomeScreen> {
        Text("Home screen")
    }
}

@Serializable
object HomeScreen

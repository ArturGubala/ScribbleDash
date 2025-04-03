package com.scribbledash.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import com.scribbledash.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HomeScreen, navOptions)

fun NavGraphBuilder.homeScreen(
    onBackClick: () -> Unit
) {
    composable<HomeScreen> {
        HomeRoute(
            onBackClick = onBackClick
        )
    }
}

@Serializable
object HomeScreen

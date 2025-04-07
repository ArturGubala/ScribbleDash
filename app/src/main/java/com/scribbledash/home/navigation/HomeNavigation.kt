package com.scribbledash.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.core.presentation.navigation.Route
import kotlinx.serialization.Serializable
import com.scribbledash.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HomeScreen, navOptions)

fun NavGraphBuilder.homeScreen(
    navController: NavController
) {
    composable<HomeScreen> {
        HomeRoute(
            navController = navController
        )
    }
}

@Serializable
object HomeScreen : Route

package com.scribbledash.shop.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.shop.ShopRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToShop(navOptions: NavOptions? = null) = navigate(ShopScreen, navOptions)

fun NavGraphBuilder.shopScreen(
    navController: NavController
) {
    composable<ShopScreen> {
        ShopRoute(
            navController = navController
        )
    }
}

@Serializable
object ShopScreen : Route

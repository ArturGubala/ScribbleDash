package com.scribbledash.core.presentation.navigation


import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun ScribbleDashNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = modifier
    ) {
        TopLevelDestination.entries.forEach { destination ->
            val isSelected = navController
                .currentDestination
                .isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.destination) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = if (isSelected) destination.selectedIcon else destination.unselectedIcon),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ScribbleDashNavigationDefaults.navigationSelectedItemColor(),
                    unselectedIconColor = ScribbleDashNavigationDefaults.navigationContentColor(),
                    selectedTextColor = ScribbleDashNavigationDefaults.navigationSelectedItemColor(),
                    unselectedTextColor = ScribbleDashNavigationDefaults.navigationContentColor(),
                    indicatorColor = ScribbleDashNavigationDefaults.navigationIndicatorColor(),
                )
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) == true
    } == true

object ScribbleDashNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

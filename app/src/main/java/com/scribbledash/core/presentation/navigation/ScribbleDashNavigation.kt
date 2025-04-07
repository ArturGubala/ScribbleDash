package com.scribbledash.core.presentation.navigation


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.scribbledash.ScribbleDashAppState
import com.scribbledash.ui.theme.MenuItemSelected
import com.scribbledash.ui.theme.MenuItemUnselected

@Composable
fun ScribbleDashNavigation(
    appState: ScribbleDashAppState,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = modifier
    ) {
        val currentDestination = appState.currentDestination

        appState.topLevelDestinations.forEach { destination ->
            val isSelected =
                currentDestination
                .isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    appState.navigateToTopLevelDestination(destination)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = if (isSelected) destination.selectedIcon else destination.unselectedIcon),
                        contentDescription = null,
                        tint = if (isSelected) MenuItemSelected else MenuItemUnselected
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

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) == true
    } == true

object ScribbleDashNavigationDefaults {
    @Composable
    fun navigationContentColor() = Color.Transparent

    @Composable
    fun navigationSelectedItemColor() = MenuItemSelected

    @Composable
    fun navigationIndicatorColor() = Color.Transparent
}

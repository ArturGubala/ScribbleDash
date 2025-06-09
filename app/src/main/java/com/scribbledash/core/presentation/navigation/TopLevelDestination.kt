package com.scribbledash.core.presentation.navigation

import androidx.compose.ui.graphics.Color
import com.scribbledash.R
import com.scribbledash.home.navigation.HomeScreen
import com.scribbledash.statistic.navigation.StatisticScreen

enum class TopLevelDestination(
    val destination: Any,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val selectedColor: Color,
    val unselectedColor: Color
) {
    STATISTIC(
        destination = StatisticScreen,
        selectedIcon = R.drawable.ic_statistic,
        unselectedIcon = R.drawable.ic_statistic,
        selectedColor = Color(0xFFFA852C),
        unselectedColor = Color(0xFFE1D5CA)
    ),
    HOME(
        destination = HomeScreen,
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home,
        selectedColor = Color(0xFF238CFF),
        unselectedColor = Color(0xFFE1D5CA)
    ),
    SHOP(
        destination = HomeScreen,
        selectedIcon = R.drawable.ic_shop,
        unselectedIcon = R.drawable.ic_shop,
        selectedColor = Color(0xFFAB5CFA),
        unselectedColor = Color(0xFFE1D5CA)
    )
}

package com.scribbledash.core.presentation.navigation

import com.scribbledash.R
import com.scribbledash.home.navigation.HomeScreen
import com.scribbledash.statistic.navigation.StatisticScreen

enum class TopLevelDestination(
    val destination: Any,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    STATISTIC(
        destination = StatisticScreen,
        selectedIcon = R.drawable.ic_statistic,
        unselectedIcon = R.drawable.ic_statistic
    ),
    HOME(
        destination = HomeScreen,
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home
    )
}

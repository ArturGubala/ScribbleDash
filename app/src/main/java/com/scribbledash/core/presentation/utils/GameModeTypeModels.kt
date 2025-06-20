package com.scribbledash.core.presentation.utils

import androidx.compose.runtime.Stable
import com.scribbledash.core.domain.model.GameModeType.OneRoundWonder
import com.scribbledash.core.presentation.navigation.Route
import com.scribbledash.gameplay.navigation.GameplayScreen

@Stable
sealed class GameModeTypeUiModel(
    val route: Route
) {

    data object OneRoundWonder : GameModeTypeUiModel(
        route = GameplayScreen
    )
}

fun String.toGameModeTypeUiModel(): GameModeTypeUiModel {
    return when (this) {
        OneRoundWonder.mode -> GameModeTypeUiModel.OneRoundWonder
        else -> GameModeTypeUiModel.OneRoundWonder
    }
}

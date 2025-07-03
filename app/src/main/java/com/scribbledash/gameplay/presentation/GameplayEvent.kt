package com.scribbledash.gameplay.presentation

import com.scribbledash.core.presentation.utils.GameType

interface GameplayEvent {
    object NavigateBackToHome : GameplayEvent
    object NavigateToResult : GameplayEvent
    data class NavigateToDifficultyLevelScreen(val gameType: GameType) : GameplayEvent
}

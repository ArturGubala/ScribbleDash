package com.scribbledash.gameplay.presentation

import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType

interface GameplayEvent {
    object NavigateBackToHome : GameplayEvent
    object NavigateToResult : GameplayEvent
    data class NavigateToDifficultyLevelScreen(val gameType: GameType) : GameplayEvent
    data class NavigateToGameplayScreen(val gameType: GameType, val difficultyLevel: DifficultyLevel) : GameplayEvent
    object NavigateToSummary : GameplayEvent
}

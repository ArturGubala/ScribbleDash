package com.scribbledash.gameplay.presentation

interface GameplayEvent {
    object NavigateBackToHome : GameplayEvent
    object NavigateToResult : GameplayEvent
    object NavigateToDifficultyLevelScreen : GameplayEvent
}

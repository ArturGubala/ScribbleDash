package com.scribbledash.core.presentation.screens.difficultylevel

interface DifficultyLevelEvent {
    object NavigateToDestination: DifficultyLevelEvent
    object NavigateBackToHome : DifficultyLevelEvent
}

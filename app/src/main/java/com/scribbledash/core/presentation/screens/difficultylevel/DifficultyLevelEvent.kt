package com.scribbledash.core.presentation.screens.difficultylevel

import com.scribbledash.core.presentation.utils.DifficultyLevel

interface DifficultyLevelEvent {
    data class NavigateToGameplay(val difficultyLevel: DifficultyLevel): DifficultyLevelEvent
    object NavigateBackToHome : DifficultyLevelEvent
}

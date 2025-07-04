package com.scribbledash.core.presentation.screens.difficultylevel

import com.scribbledash.core.presentation.utils.DifficultyLevel

interface DifficultyLevelAction {
    data class OnDifficultyLevelClick(val difficultyLevel: DifficultyLevel) : DifficultyLevelAction
    data object OnBackClicked : DifficultyLevelAction
}

package com.scribbledash.core.presentation.screens.difficultylevel

interface DifficultyLevelAction {
    object OnDifficultyLevelClick : DifficultyLevelAction
    data object OnBackClicked : DifficultyLevelAction
}

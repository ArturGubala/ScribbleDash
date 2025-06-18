package com.scribbledash.gameplay.presentation

import androidx.compose.ui.geometry.Offset

interface GameplayAction {
    data object OnBackClicked : GameplayAction
    data object OnStartDrawing: GameplayAction
    data class OnDrawing(val offset: Offset): GameplayAction
    data object OnStopDrawing: GameplayAction
    data object OnUndoClick: GameplayAction
    data object OnRedoClick: GameplayAction
    data object OnDoneClick: GameplayAction
    data object ShowPreview: GameplayAction
}

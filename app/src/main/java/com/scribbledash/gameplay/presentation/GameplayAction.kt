package com.scribbledash.gameplay.presentation

import androidx.compose.ui.geometry.Offset
import com.scribbledash.core.presentation.utils.GameType

interface GameplayAction {
    data object OnBackClicked : GameplayAction
    data object OnStartDrawing: GameplayAction
    data class OnDrawing(val offset: Offset): GameplayAction
    data object OnStopDrawing: GameplayAction
    data object OnUndoClick: GameplayAction
    data object OnRedoClick: GameplayAction
    data object OnDoneClick: GameplayAction
    data object ShowPreview: GameplayAction
    data class OnTryAgainClick(val gameType: GameType): GameplayAction
}

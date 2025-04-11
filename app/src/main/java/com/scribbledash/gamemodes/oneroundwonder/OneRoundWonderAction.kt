package com.scribbledash.gamemodes.oneroundwonder

import androidx.compose.ui.geometry.Offset

interface OneRoundWonderAction {
    data object OnBackClicked : OneRoundWonderAction
    data object OnStartDrawing: OneRoundWonderAction
    data class OnDrawing(val offset: Offset): OneRoundWonderAction
    data object OnStopDrawing: OneRoundWonderAction
    data object OnUndoClick: OneRoundWonderAction
    data object OnRedoClick: OneRoundWonderAction
    data object OnClearCanvasClick: OneRoundWonderAction
}

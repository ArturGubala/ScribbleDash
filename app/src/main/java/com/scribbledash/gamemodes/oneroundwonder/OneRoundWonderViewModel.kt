package com.scribbledash.gamemodes.oneroundwonder

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OneRoundWonderViewModel(): ViewModel() {

    private val _state = MutableStateFlow(OneRoundWonderState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            OneRoundWonderState(),
        )

    private val eventChannel = Channel<OneRoundWonderEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: OneRoundWonderAction) {
        when(action) {
            OneRoundWonderAction.OnBackClicked -> onBackClicked()
            OneRoundWonderAction.OnStartDrawing -> onStartDrawing()
            is OneRoundWonderAction.OnDrawing -> onDrawing(action.offset)
            OneRoundWonderAction.OnStopDrawing -> onStopDrawing()
            OneRoundWonderAction.OnUndoClick -> onUndoClick()
            OneRoundWonderAction.OnRedoClick -> onRedoClick()
            OneRoundWonderAction.OnClearCanvasClick -> onClearCanvasClick()
        }
    }

    private fun onBackClicked() {
        viewModelScope.launch {
            eventChannel.send(OneRoundWonderEvent.NavigateBackToHome)
        }
    }

    private fun onStartDrawing() {
        _state.update { it.copy(
            currentPath = PathData(
                path = emptyList()
            )
        ) }
    }

    private fun onDrawing(offset: Offset) {
        val currentPathData = state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = currentPathData.copy(
                path = currentPathData.path + offset
            )
        ) }
    }

    private fun onStopDrawing() {
        val currentPathData = state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = null,
            paths = it.paths + currentPathData
        ) }
    }

    private fun onUndoClick() {

    }

    private fun onRedoClick() {

    }

    private fun onClearCanvasClick() {
        _state.update { it.copy(
            currentPath = null,
            paths = emptyList()
        ) }
    }
}

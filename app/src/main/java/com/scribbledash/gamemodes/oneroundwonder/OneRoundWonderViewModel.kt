package com.scribbledash.gamemodes.oneroundwonder

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
            OneRoundWonderAction.ShowPreview -> showPreview()
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
        val newUndoPaths = _state.value.undoPaths.apply {
            if (size >= 5) removeFirst()
            addLast(currentPathData)
        }
        _state.update { it.copy(
            currentPath = null,
            paths = it.paths + currentPathData,
            undoPaths = ArrayDeque(newUndoPaths),
            redoPaths = ArrayDeque()
        ) }
    }

    private fun onUndoClick() {
        val undoStack = _state.value.undoPaths
        val currentPaths = _state.value.paths

        if (undoStack.isNotEmpty() && currentPaths.isNotEmpty()) {
            val lastPath = undoStack.removeLast()
            val newPaths = currentPaths.dropLast(1)
            val newRedoPaths = _state.value.redoPaths.apply {
                if (size >= 5) removeFirst()
                addLast(lastPath)
            }

            _state.update {
                it.copy(
                    paths = newPaths,
                    undoPaths = ArrayDeque(undoStack),
                    redoPaths = ArrayDeque(newRedoPaths)
                )
            }
        }
    }

    private fun onRedoClick() {
        val redoStack = _state.value.redoPaths

        if (redoStack.isNotEmpty()) {
            val lastPath = redoStack.removeLast()
            val newPaths = _state.value.paths + lastPath
            val newUndoPaths = _state.value.undoPaths.apply {
                if (size >= 5) removeFirst()
                addLast(lastPath)
            }

            _state.update {
                it.copy(
                    paths = newPaths,
                    undoPaths = ArrayDeque(newUndoPaths),
                    redoPaths = ArrayDeque(redoStack)
                )
            }
        }
    }

    private fun onClearCanvasClick() {
        _state.update {
            it.copy(
                currentPath = null,
                paths = emptyList(),
                undoPaths = ArrayDeque(),
                redoPaths = ArrayDeque()
            )
        }
    }

    private fun showPreview() {
        viewModelScope.launch {
            _state.update { it.copy(isPreviewVisible = true) }
            for (second in 3 downTo 1) {
                _state.update { it.copy(remainingTime = second) }
                delay(1000L)
            }
            _state.update { it.copy(isPreviewVisible = false) }
        }
    }
}

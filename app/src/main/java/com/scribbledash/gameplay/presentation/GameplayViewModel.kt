package com.scribbledash.gameplay.presentation

import android.graphics.Path
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.core.domain.model.Drawings
import com.scribbledash.core.presentation.utils.getDrawableRawIdForDrawing
import com.scribbledash.gameplay.model.PathData
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.calculatePathLength
import com.scribbledash.gameplay.model.computeBounds
import com.scribbledash.gameplay.model.normalizeForComparison
import com.scribbledash.gameplay.model.toBitmap
import com.scribbledash.gameplay.utils.BitmapExtensions
import com.scribbledash.gameplay.utils.VectorXmlParser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameplayViewModel(
    private val vectorXmlParser: VectorXmlParser,
    private val bitmapExtensions: BitmapExtensions
): ViewModel() {

    private val _state = MutableStateFlow(GameplayState())
    val state = _state
        .onStart {
            loadDrawings()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            GameplayState(),
        )

    private val _drawings = MutableStateFlow<List<ScribbleDashPath>>(emptyList())
    val drawings: StateFlow<List<ScribbleDashPath>> = _drawings

    private val eventChannel = Channel<GameplayEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GameplayAction) {
        when(action) {
            GameplayAction.OnBackClicked -> onBackClicked()
            GameplayAction.OnStartDrawing -> onStartDrawing()
            is GameplayAction.OnDrawing -> onDrawing(action.offset)
            GameplayAction.OnStopDrawing -> onStopDrawing()
            GameplayAction.OnUndoClick -> onUndoClick()
            GameplayAction.OnRedoClick -> onRedoClick()
            GameplayAction.OnDoneClick -> compareDrawings()
            GameplayAction.ShowPreview -> showPreview()
        }
    }

    private fun onBackClicked() {
        viewModelScope.launch {
            eventChannel.send(GameplayEvent.NavigateBackToHome)
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

    private fun loadDrawings() {
        viewModelScope.launch {
            val drawings = Drawings.entries.map {
                val drawable = getDrawableRawIdForDrawing(it)
                vectorXmlParser.loadPathsFromRawXml(drawable)
            }
            _drawings.value = drawings
            _state.update {
                it.copy(
                    drawings = drawings,
                    previewDrawing = drawings.random()
                )
            }
        }
    }

    private fun compareDrawings() {
        val previewDrawing = ScribbleDashPath(
            path = Path(_state.value.previewDrawing!!.path),
            bounds = _state.value.previewDrawing!!.bounds
        )

        val userDrawing = toAndroidPath(_state.value.paths)
        val normalizedUserDrawing = userDrawing
            .normalizeForComparison(Size(500f, 500f), userDrawing.computeBounds())
        val normalizedReferenceDrawing = previewDrawing.path
            .normalizeForComparison(Size(500f, 500f), previewDrawing.bounds)

        var userBitmap = normalizedUserDrawing.toBitmap(stroke = 20f)
        var referenceBitmap = normalizedReferenceDrawing.toBitmap()

        val comparisonResult = bitmapExtensions.compareBitmaps(
            drawing = userBitmap,
            reference = referenceBitmap
        )

        val userDrawingLength = userDrawing.calculatePathLength()
        val referenceDrawingLength = previewDrawing.path.calculatePathLength()
        val lengthRatio = userDrawingLength / referenceDrawingLength

        val lengthPenalty = if (lengthRatio < 0.7f) {
            (0.7f - lengthRatio)
        } else 0f

        val rawScore = comparisonResult - lengthPenalty
        _state.update {
            it.copy(
                score = rawScore.coerceIn(0f, 100f)
            )
        }

        Log.d("RESULT", "Score: ${"%.1f".format(_state.value.score)}%")
        viewModelScope.launch {
            eventChannel.send(GameplayEvent.NavigateToResult)
        }
    }

    private fun toAndroidPath(pathDataList: List<PathData>): Path {
        val path = Path()

        for (pathData in pathDataList) {
            val offsets = pathData.path
            if (offsets.isNotEmpty()) {
                path.moveTo(offsets.first().x, offsets.first().y)
                for (i in 1 until offsets.size) {
                    val current = offsets[i]
                    path.lineTo(current.x, current.y)
                }
            }
        }

        return path
    }
}

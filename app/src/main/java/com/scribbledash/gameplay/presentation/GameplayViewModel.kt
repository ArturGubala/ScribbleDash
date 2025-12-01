package com.scribbledash.gameplay.presentation

import android.graphics.Path
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.core.domain.model.Drawings
import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.getDrawableRawIdForDrawing
import com.scribbledash.gameplay.model.PathData
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.calculatePathLength
import com.scribbledash.gameplay.model.computeBounds
import com.scribbledash.gameplay.model.normalizeForComparison
import com.scribbledash.gameplay.model.toAndroidPath
import com.scribbledash.gameplay.model.toBitmap
import com.scribbledash.gameplay.utils.BitmapExtensions
import com.scribbledash.gameplay.utils.CountdownTimer
import com.scribbledash.gameplay.utils.VectorXmlParser
import kotlinx.coroutines.channels.Channel
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
    fun setGameConfiguration(gameType: GameType, difficultyLevel: DifficultyLevel) {
        when (_state.value.gameType) {
            GameType.ONE_ROUND_WONDER -> {
                _state.update {
                    it.copy(
                        gameType = gameType,
                        difficultyLevel = difficultyLevel
                    )
                }
            }
            GameType.SPEED_DRAW -> {
                clearCanvas()
                showPreview()
            }
            GameType.ENDLESS_MODE -> TODO()
        }
    }

    private val speedDrawGameModeTimer = CountdownTimer(
        coroutineScope = viewModelScope,
        onTick = { time ->
            _state.update {
                it.copy(
                    remainingCountdownTime = time
                )
            }
        },
        onFinished = {
            compareDrawings(gameType = GameType.SPEED_DRAW)
            viewModelScope.launch {
                eventChannel.send(GameplayEvent.NavigateToSummary)
            }
        }
    )

    private val previewTimer = CountdownTimer(
        coroutineScope = viewModelScope,
        onTick = { time ->
            _state.update {
                it.copy(
                    remainingPreviewTime = time
                )
            }
        },
        onFinished = {
            _state.update { it.copy(isPreviewVisible = false) }
            if (_state.value.remainingCountdownTime <= 0) {
                speedDrawGameModeTimer.start(45000)
            } else {
                speedDrawGameModeTimer.resume()
            }
        }
    )

    fun onAction(action: GameplayAction) {
        when(action) {
            GameplayAction.OnBackClicked -> onBackClicked()
            GameplayAction.OnStartDrawing -> onStartDrawing()
            is GameplayAction.OnDrawing -> onDrawing(action.offset)
            GameplayAction.OnStopDrawing -> onStopDrawing()
            GameplayAction.OnUndoClick -> onUndoClick()
            GameplayAction.OnRedoClick -> onRedoClick()
            GameplayAction.OnDoneClick -> onDoneClick()
            GameplayAction.ShowPreview -> showPreview()
            is GameplayAction.OnTryAgainClick -> navigateToDifficultyLevelScreen(gameType = action.gameType)
            GameplayAction.OnDrawAgainClick -> navigateToGameplayScreen()
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

    private fun clearCanvas() {
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
        _state.update { it.copy(isPreviewVisible = true) }
        previewTimer.start(3000L)
    }

    private fun loadDrawings() {
        if (_drawings.value.isNotEmpty()) return

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

    private fun compareDrawings(gameType: GameType) {
        val previewDrawing = ScribbleDashPath(
            path = Path(_state.value.previewDrawing!!.path),
            bounds = _state.value.previewDrawing!!.bounds
        )

        val userDrawing = _state.value.paths.toAndroidPath()
        val normalizedUserDrawing = userDrawing
            .normalizeForComparison(Size(500f, 500f), userDrawing.computeBounds())
        val normalizedReferenceDrawing = previewDrawing.path
            .normalizeForComparison(Size(500f, 500f), previewDrawing.bounds)

        val difficultyMultiplier = when (_state.value.difficultyLevel) {
            DifficultyLevel.BEGINNER -> 15f
            DifficultyLevel.CHALLENGING -> 7f
            DifficultyLevel.MASTER -> 4f
        }

        val userBitmap = normalizedUserDrawing.toBitmap(stroke = 3f)
        val referenceBitmap = normalizedReferenceDrawing.toBitmap(stroke = 3f * difficultyMultiplier)

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

        when(gameType) {
            GameType.ONE_ROUND_WONDER,
            GameType.ENDLESS_MODE ->  {
                _state.update {
                    it.copy(
                        score = rawScore.coerceIn(0f, 100f)
                    )
                }
            }
            GameType.SPEED_DRAW -> {
                val currentScore = _state.value.score
                var drawingCount = _state.value.drawingCounter
                val newScore = rawScore.coerceIn(0f, 100f)

                if (newScore >= 40) {
                    _state.update {
                        it.copy(
                            score = (currentScore * drawingCount + newScore) / (drawingCount + 1),
                            drawingCounter = ++drawingCount
                        )
                    }
                }
            }
        }
    }

    private fun navigateToDifficultyLevelScreen(gameType: GameType) {
        viewModelScope.launch {
            eventChannel.send(GameplayEvent.NavigateToDifficultyLevelScreen(gameType = gameType))
        }
    }
    private fun navigateToGameplayScreen() {
        viewModelScope.launch {
            _state.update { it.copy(
                remainingCountdownTime = 0L,
                drawingCounter = 0
            ) }
            eventChannel.send(GameplayEvent.NavigateToGameplayScreen(
                gameType = _state.value.gameType,
                difficultyLevel = _state.value.difficultyLevel))
        }
    }

    private fun onDoneClick() {
        when(_state.value.gameType) {
            GameType.ONE_ROUND_WONDER -> {
                compareDrawings(gameType = GameType.ONE_ROUND_WONDER)
                viewModelScope.launch {
                    eventChannel.send(GameplayEvent.NavigateToResult)
                }
            }
            GameType.SPEED_DRAW -> {
                speedDrawGameModeTimer.pause()
                compareDrawings(gameType = GameType.SPEED_DRAW)
                _state.update { it.copy(previewDrawing = _drawings.value.random()) }
                showPreview()
                clearCanvas()
            }
            GameType.ENDLESS_MODE -> TODO()
        }
    }

}

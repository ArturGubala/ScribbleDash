package com.scribbledash.gameplay.presentation

import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.gameplay.model.PathData
import com.scribbledash.gameplay.model.ScribbleDashPath

data class GameplayState (
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: ArrayDeque<PathData> = ArrayDeque(),
    val redoPaths: ArrayDeque<PathData> = ArrayDeque(),
    val isPreviewVisible: Boolean = false,
    val remainingPreviewTime: Long = 0L,
    val remainingCountdownTime: Long = 0L,
    val drawings: List<ScribbleDashPath> = emptyList(),
    val previewDrawing: ScribbleDashPath? = null,
    val finalScore: Float = 0f,
    val lastScore: Float = 0f,
    val gameType: GameType = GameType.ONE_ROUND_WONDER,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.BEGINNER,
    val drawingCounter: Short = 0,
    val isNewBestDrawings: Boolean = false,
    val isNewBestAccuracy: Boolean = false,
    val roundCoins: Int = 0,
    val totalCoins: Int = 0,
    val strokeColor: Color = Color.Black,
    val strokeGradientColors: List<Color>? = null,
    val backgroundColor: Color = Color.White,
    val backgroundTexture: Int? = null
) {
    val isClearCanvasButtonActive: Boolean
        get() = paths.isNotEmpty()

    val isUndoButtonActive: Boolean
        get() = undoPaths.isNotEmpty()

    val isRedoButtonActive: Boolean
        get() = redoPaths.isNotEmpty()
}

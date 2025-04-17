package com.scribbledash.gamemodes.oneroundwonder

import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import kotlin.collections.isNotEmpty

data class OneRoundWonderState (
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: ArrayDeque<PathData> = ArrayDeque(),
    val redoPaths: ArrayDeque<PathData> = ArrayDeque(),
    val isPreviewVisible: Boolean = false,
    val remainingTime: Int = 0
) {
    val isClearCanvasButtonActive: Boolean
        get() = paths.isNotEmpty()

    val isUndoButtonActive: Boolean
        get() = undoPaths.isNotEmpty()

    val isRedoButtonActive: Boolean
        get() = redoPaths.isNotEmpty()
}

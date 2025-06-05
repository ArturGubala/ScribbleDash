package com.scribbledash.gamemodes.oneroundwonder.components

import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.scribbledash.gamemodes.oneroundwonder.OneRoundWonderAction
import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import com.scribbledash.ui.theme.ScribbleDashTheme
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun ScribbleDashDrawingArea(
    currentPath: PathData?,
    paths: List<PathData>,
    onAction: (OneRoundWonderAction) -> Unit,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Black,
    canDrawing: Boolean = true,
    width: Float = 0f,
    height: Float = 0f
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(36.dp),
                ambientColor = Color(0x1F726558),
                spotColor = Color(0x1F726558)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(36.dp)
            )
            .padding(all = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFF6F1EC),
                    shape = RoundedCornerShape(24.dp)
                )
                .drawWithContent {
                    drawContent()

                    val strokeWidth = 1.dp.toPx()
                    val lineColor = Color(0x0D000000)

                    val cols = 3
                    val rows = 3

                    val cellWidth = size.width / cols
                    val cellHeight = size.height / rows

                    for (col in 1 until cols) {
                        val x = (cellWidth * col).roundToInt().toFloat()
                        drawLine(
                            color = lineColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = strokeWidth
                        )
                    }

                    for (row in 1 until rows) {
                        val y = (cellHeight * row).roundToInt().toFloat()
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                }
        ) {
            Canvas(
                modifier = modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .let {
                        if (canDrawing) {
                            it.pointerInput(true) {
                                detectDragGestures(
                                    onDragStart = {
                                        onAction(OneRoundWonderAction.OnStartDrawing)
                                    },
                                    onDragEnd = {
                                        onAction(OneRoundWonderAction.OnStopDrawing)
                                    },
                                    onDrag = { change, _ ->
                                        onAction(OneRoundWonderAction.OnDrawing(change.position))
                                    },
                                    onDragCancel = {
                                        onAction(OneRoundWonderAction.OnStopDrawing)
                                    },
                                )
                            }
                        } else it
                    }
            ) {
                if (canDrawing) {
                    paths.fastForEach { pathData ->
                        drawPath(
                            path = pathData.path,
                            color = strokeColor,
                        )
                    }
                    currentPath?.let {
                        drawPath(
                            path = it.path,
                            color = strokeColor
                        )
                    }
                } else {
                    val totalBounds = computeTotalBounds(paths)

                    val drawingWidth = totalBounds.width()
                    val drawingHeight = totalBounds.height()

                    val scaleFactor = min(
                        size.width / width,
                        size.height / height
                    )

                    val translateX = (size.width - drawingWidth * scaleFactor) / 2f
                    val translateY = (size.height - drawingHeight * scaleFactor) / 2f

                    val matrix = Matrix().apply {
                        postTranslate(-totalBounds.left, -totalBounds.top)
                        postScale(scaleFactor, scaleFactor)
                        postTranslate(translateX, translateY)

                    }

                    paths.fastForEach { pathData ->
                        val transformed = pathData.path.transformOffsets(matrix)
                        drawPath(
                            path = transformed,
                            color = strokeColor,
                            smoothness = 1
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ScribbleDashDrawingAreaPreview() {
    ScribbleDashTheme {
        ScribbleDashDrawingArea(
            currentPath = null,
            paths = emptyList(),
            onAction = {},
            modifier = Modifier
                .height(360.dp)
                .width(354.dp)
        )
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    smoothness: Int = 5,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if(path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            for(i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if(dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

fun AndroidPath.asComposePath(): Path {
    return this.asComposePath()
}

fun List<Offset>.transformOffsets(matrix: Matrix): List<Offset> {
    return map {
        val pts = floatArrayOf(it.x, it.y)
        matrix.mapPoints(pts)
        Offset(pts[0], pts[1])
    }
}

fun computeTotalBounds(paths: List<PathData>): RectF {
    val totalBounds = RectF()

    for (pathData in paths) {
        val offsets = pathData.path
        if (offsets.isEmpty()) continue

        val minX = offsets.minOf { it.x }
        val maxX = offsets.maxOf { it.x }
        val minY = offsets.minOf { it.y }
        val maxY = offsets.maxOf { it.y }

        val rect = RectF(minX, minY, maxX, maxY)
        totalBounds.union(rect)
    }

    return totalBounds
}


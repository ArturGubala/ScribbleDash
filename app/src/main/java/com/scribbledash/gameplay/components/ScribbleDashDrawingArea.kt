package com.scribbledash.gameplay.components

import android.graphics.Path
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.scribbledash.R
import com.scribbledash.gameplay.presentation.GameplayAction
import com.scribbledash.gameplay.model.PathData
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.adjustToCanvas
import com.scribbledash.ui.theme.ScribbleDashTheme
import kotlin.math.abs
import kotlin.math.roundToInt
import androidx.compose.ui.graphics.Path as ComposePath

@Composable
fun ScribbleDashDrawingArea(
    currentPath: PathData?,
    paths: List<PathData>,
    exampleDrawing: ScribbleDashPath?,
    onAction: (GameplayAction) -> Unit,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Black,
    strokeGradientColors: List<Color>? = null,
    canDrawing: Boolean = true,
    backgroundColor: Color = Color.White,
    @DrawableRes backgroundTexture: Int? = null
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
                .clip(shape = RoundedCornerShape(24.dp))
                .then(
                    if (backgroundTexture != null) {
                        Modifier
                            .paint(
                                painter = painterResource(id = backgroundTexture),
                                contentScale = ContentScale.FillBounds
                            )
                    } else {
                        Modifier.background(
                            color = backgroundColor,
                        )
                    }
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
                                        onAction(GameplayAction.OnStartDrawing)
                                    },
                                    onDragEnd = {
                                        onAction(GameplayAction.OnStopDrawing)
                                    },
                                    onDrag = { change, _ ->
                                        onAction(GameplayAction.OnDrawing(change.position))
                                    },
                                    onDragCancel = {
                                        onAction(GameplayAction.OnStopDrawing)
                                    },
                                )
                            }
                        } else it
                    }
            ) {
                if (canDrawing) {
                    val brush = if (!strokeGradientColors.isNullOrEmpty()) {
                        Brush.horizontalGradient(
                            colors = strokeGradientColors,
                            startX = 0f,
                            endX = size.width
                        )
                    } else {
                        SolidColor(strokeColor)
                    }

                    paths.fastForEach { pathData ->
                        drawPath(
                            path = pathData.path,
                            brush = brush,
                        )
                    }
                    currentPath?.let {
                        drawPath(
                            path = it.path,
                            brush = brush
                        )
                    }
                } else {
                    val brush = if (!strokeGradientColors.isNullOrEmpty()) {
                        Brush.horizontalGradient(
                            colors = strokeGradientColors,
                            startX = 0f,
                            endX = size.width
                        )
                    } else {
                        SolidColor(strokeColor)
                    }

                    drawPath(
                        path = exampleDrawing!!.path
                            .adjustToCanvas(canvasSize = size, bounds = exampleDrawing.bounds)
                            .asComposePath(),
                        brush = brush,
                        style = Stroke(width = 10f)
                    )
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
            exampleDrawing = null,
            onAction = {},
            modifier = Modifier
                .height(360.dp)
                .width(354.dp)
        )
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    brush: Brush,
    smoothness: Int = 5,
    thickness: Float = 10f
) {
    val smoothedPath = ComposePath().apply {
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
        brush = brush,
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

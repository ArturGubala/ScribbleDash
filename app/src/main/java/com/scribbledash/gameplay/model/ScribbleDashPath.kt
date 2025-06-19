package com.scribbledash.gameplay.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.createBitmap
import kotlin.math.min

data class ScribbleDashPath(val path: Path, val bounds: RectF)

fun Path.computeBounds(precision: Float = 0.5f): RectF {
    val approximation = this.approximate(precision)

    if (approximation.isEmpty()) return RectF()

    var minX = Float.MAX_VALUE
    var minY = Float.MAX_VALUE
    var maxX = Float.MIN_VALUE
    var maxY = Float.MIN_VALUE

    for (i in 1 until approximation.size step 3) {
        val x = approximation[i]
        val y = approximation[i + 1]
        minX = minOf(minX, x)
        minY = minOf(minY, y)
        maxX = maxOf(maxX, x)
        maxY = maxOf(maxY, y)
    }

    return RectF(minX, minY, maxX, maxY)
}

fun Path.adjustToCanvas(canvasSize: Size, bounds: RectF): Path {
    val newPath = Path(this)

    val drawingWidth = bounds.width()
    val drawingHeight = bounds.height()

    val scaleFactor = min(
        canvasSize.width * .8f / drawingWidth,
        canvasSize.height * .8f / drawingHeight
    )

    val translateX = (canvasSize.width - drawingWidth * scaleFactor) / 2f
    val translateY = (canvasSize.height - drawingHeight * scaleFactor) / 2f

    val matrix = Matrix().apply {
        postTranslate(-bounds.left, -bounds.top)
        postScale(scaleFactor, scaleFactor)
        postTranslate(translateX, translateY)
    }

    newPath.transform(matrix)
    return newPath
}

fun Path.normalizeForComparison(canvasSize: Size, bounds: RectF): Path {
    val newPath = Path(this)

    val scaleX = (canvasSize.width * (1 - 2 * .1f)) / bounds.width()
    val scaleY = (canvasSize.width * (1 - 2 * .1f)) / bounds.height()
    val scale = min(scaleX, scaleY)

    val dx = -bounds.left
    val dy = -bounds.top

    val tx = (canvasSize.width - bounds.width() * scale) / 2f
    val ty = (canvasSize.width - bounds.height() * scale) / 2f

    val matrix = Matrix().apply {
        postTranslate(dx, dy)
        postScale(scale, scale)
        postTranslate(tx, ty)
    }

    newPath.transform(matrix)
    return newPath
}

fun Path.toBitmap(
    canvasSize: IntSize = IntSize(500, 500),
    stroke: Float = 10f
): Bitmap {
    val bitmap = createBitmap(canvasSize.width, canvasSize.height)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        color = android.graphics.Color.YELLOW
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = stroke
    }

    canvas.drawPath(this, paint)
    return bitmap
}

fun Path.calculatePathLength(): Float {
    val pathMeasure = PathMeasure(this, false)
    var length = 0f

    do {
        length += pathMeasure.length
    } while (pathMeasure.nextContour())

    return length
}

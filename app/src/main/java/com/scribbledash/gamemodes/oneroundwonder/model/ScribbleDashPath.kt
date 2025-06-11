package com.scribbledash.gamemodes.oneroundwonder.model

import android.graphics.Path;
import android.graphics.RectF;

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


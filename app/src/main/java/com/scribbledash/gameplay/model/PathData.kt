package com.scribbledash.gameplay.model

import android.graphics.Path
import androidx.compose.ui.geometry.Offset

data class PathData( val path: List<Offset> )

fun List<PathData>.toAndroidPath(): Path {
    val path = Path()

    for (pathData in this) {
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

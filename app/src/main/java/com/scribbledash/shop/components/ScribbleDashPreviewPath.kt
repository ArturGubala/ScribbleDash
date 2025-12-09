package com.scribbledash.shop.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashPreviewPath(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val originalWidth = 94f
        val originalHeight = 70f

        val scaleX = width / originalWidth
        val scaleY = height / originalHeight
        val scale = minOf(scaleX, scaleY)

        val offsetX = (width - originalWidth * scale) / 2f
        val offsetY = (height - originalHeight * scale) / 2f

        val path = Path().apply {
            moveTo(
                15.0908f * scale + offsetX,
                39.3624f * scale + offsetY
            )

            cubicTo(
                14.9008f * scale + offsetX, 36.9431f * scale + offsetY,
                14.9952f * scale + offsetX, 35.5345f * scale + offsetY,
                15.5062f * scale + offsetX, 33.1622f * scale + offsetY
            )

            cubicTo(
                16.4025f * scale + offsetX, 29.0014f * scale + offsetY,
                18.4077f * scale + offsetX, 26.2524f * scale + offsetY,
                20.4313f * scale + offsetX, 23.4404f * scale + offsetY
            )

            cubicTo(
                21.4885f * scale + offsetX, 21.9713f * scale + offsetY,
                25.0074f * scale + offsetX, 19.3826f * scale + offsetY,
                25.8783f * scale + offsetX, 22.393f * scale + offsetY
            )

            cubicTo(
                27.7023f * scale + offsetX, 28.6983f * scale + offsetY,
                27.77f * scale + offsetX, 34.9875f * scale + offsetY,
                27.77f * scale + offsetX, 43.1132f * scale + offsetY
            )

            cubicTo(
                27.77f * scale + offsetX, 45.4903f * scale + offsetY,
                27.5124f * scale + offsetX, 50.2829f * scale + offsetY,
                30.4446f * scale + offsetX, 48.6778f * scale + offsetY
            )

            cubicTo(
                34.3048f * scale + offsetX, 46.5647f * scale + offsetY,
                36.6439f * scale + offsetX, 32.1574f * scale + offsetY,
                41.893f * scale + offsetX, 36.1082f * scale + offsetY
            )

            cubicTo(
                44.6529f * scale + offsetX, 38.1856f * scale + offsetY,
                48.5566f * scale + offsetX, 41.8857f * scale + offsetY,
                51.841f * scale + offsetX, 42.7531f * scale + offsetY
            )

            cubicTo(
                56.4142f * scale + offsetX, 43.9609f * scale + offsetY,
                58.896f * scale + offsetX, 42.9265f * scale + offsetY,
                63.6058f * scale + offsetX, 42f * scale + offsetY
            )

            cubicTo(
                69.579f * scale + offsetX, 40.825f * scale + offsetY,
                73.3363f * scale + offsetX, 39.0992f * scale + offsetY,
                79f * scale + offsetX, 36.8667f * scale + offsetY
            )
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 3f * scale,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScribblePreviewDashPreviewPath() {
    ScribbleDashTheme {
        Box(modifier = Modifier.size(94.dp, 70.dp)) {
            ScribbleDashPreviewPath(
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScribblePreviewDifferentColorsDashPreviewPath() {
    ScribbleDashTheme {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Box(modifier = Modifier.size(80.dp, 60.dp)) {
                ScribbleDashPreviewPath(color = Color.Black, modifier = Modifier.fillMaxSize())
            }
            Box(modifier = Modifier.size(80.dp, 60.dp)) {
                ScribbleDashPreviewPath(color = Color.Red, modifier = Modifier.fillMaxSize())
            }
            Box(modifier = Modifier.size(80.dp, 60.dp)) {
                ScribbleDashPreviewPath(color = Color(0xFF6200EE), modifier = Modifier.fillMaxSize())
            }
        }
    }
}

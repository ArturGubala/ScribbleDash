package com.scribbledash.gameplay.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scribbledash.ui.theme.BagelFatOne
import com.scribbledash.ui.theme.ScribbleDashTheme
import com.scribbledash.ui.theme.Text

@SuppressLint("DefaultLocale")
@Composable
fun TimerDisplay(
    remainingTimeMs: Long,
    modifier: Modifier = Modifier,
) {
    val totalSeconds = remainingTimeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val timeText = String.format("%d:%02d", minutes, seconds)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeText,
            style = TextStyle(
                fontFamily = BagelFatOne,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = Text
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun TimerDisplayPreview() {
    ScribbleDashTheme {
        TimerDisplay(
            remainingTimeMs = 220000L,
            modifier = Modifier
                .size(48.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(50.dp),
                    ambientColor = Color(0x1F726558),
                    spotColor = Color(0x1F726558)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

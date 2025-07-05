package com.scribbledash.gameplay.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.ui.theme.BagelFatOne
import com.scribbledash.ui.theme.ScribbleDashTheme
import com.scribbledash.ui.theme.Text

@SuppressLint("DefaultLocale")
@Composable
fun TimerDisplay(
    remainingTimeMs: Long,
    modifier: Modifier = Modifier
) {
    val totalSeconds = remainingTimeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val timeText = String.format("%d:%02d", minutes, seconds)

    ScribbleDashScreenTitle(
        headline = {
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
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
fun TimerDisplayPreview() {
    ScribbleDashTheme {
        TimerDisplay(220000L)
    }
}

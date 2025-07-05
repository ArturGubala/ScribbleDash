package com.scribbledash.gameplay.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle

@Composable
fun ScribbleDashPreviewCounter(
    remainingTimeMs: Long,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val secondsInt = (remainingTimeMs / 1000).toInt()

    val text = if (remainingTimeMs > 0) {
        context.resources.getQuantityString(
            R.plurals.seconds_left,
            secondsInt,
            secondsInt
        )
    } else {
        context.getString(R.string.remaining_time)
    }

    ScribbleDashScreenTitle(
        headline = {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

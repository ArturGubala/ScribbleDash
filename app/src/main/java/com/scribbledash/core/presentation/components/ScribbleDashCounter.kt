package com.scribbledash.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.scribbledash.R

@Composable
fun CountdownText(countdown: Int) {
    val context = LocalContext.current
    val text =
        if (countdown > 0) {
            context.resources.getQuantityString(R.plurals.seconds_left, countdown, countdown)
        } else {
            context.resources.getText(R.string.remaining_time)
        }

        ScribbleDashScreenTitle(
            headline = {
                Text(
                    text = text.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
}

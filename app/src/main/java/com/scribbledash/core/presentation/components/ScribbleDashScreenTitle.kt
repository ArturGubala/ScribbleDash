package com.scribbledash.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashScreenTitle(
    headline: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    subtitle: @Composable (() -> Unit) = {}
) {
    Column(
        modifier = modifier
            .requiredHeightIn(76.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        headline()
        subtitle()
    }
}

@Preview
@Composable
fun ScribbleDashScreenTitlePreview() {
    ScribbleDashTheme {
        ScribbleDashScreenTitle(
            headline = {
                Text(
                    text = stringResource(R.string.start_drawing),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            subtitle = {
                Text(
                    text = stringResource(R.string.select_game_mode),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

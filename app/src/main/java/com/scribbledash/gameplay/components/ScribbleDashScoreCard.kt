package com.scribbledash.gameplay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashScoreCard(
    score: Short,
    feedbackTitle: String,
    feedbackDescription: String,
    modifier: Modifier = Modifier,
    isHighScore: Boolean = false
) {
    Column(
        modifier = modifier
            .width(width = 380.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF6F1EC),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$score%",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 66.sp)
            )
        }

        ScribbleDashScreenTitle(
            headline = {
                Text(
                    text = feedbackTitle,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier.fillMaxWidth(),
            subtitle = {
                Text(
                    text = feedbackDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        ScribbleDashDrawingCounter(
            value = "5",
            modifier = Modifier
                .width(76.dp)
        )
    }
}

@Preview
@Composable
private fun ScribbleDashScoreCardPreview() {
    ScribbleDashTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            ScribbleDashScoreCard(
                score = 45,
                feedbackTitle = "Meh",
                feedbackDescription = "This is what happens when you let a cat hold the pencil!",
                isHighScore = false
            )
            ScribbleDashScoreCard(
                score = 79,
                feedbackTitle = "Good",
                feedbackDescription = "Not too shabby! Keep at it, and soon you'll be the talk of the art world!",
                isHighScore = true
            )
        }
    }
}

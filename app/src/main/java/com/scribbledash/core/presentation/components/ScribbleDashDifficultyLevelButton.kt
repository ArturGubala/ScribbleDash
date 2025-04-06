package com.scribbledash.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashDifficultyLevelButton(
    image: @Composable () -> Unit,
    @StringRes description: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageAlignment: Alignment = Alignment.Center
) {
    Column(
        modifier = modifier
            .width(88.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = modifier
                .size(88.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(50.dp),
                    ambientColor = Color(0x1F726558),
                    spotColor = Color(0x1F726558)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50.dp)
                )
                .clip(RoundedCornerShape(50.dp))
                .clickable(onClick = onClick),
            contentAlignment = imageAlignment
        ) {
            image()
        }
        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScribbleDashDifficultyLevelButtonPreview() {
    ScribbleDashTheme {
        Row {
            ScribbleDashDifficultyLevelButton(
                image = {
                    Image(
                        painter = painterResource(R.drawable.ic_beginner),
                        contentDescription = null,
                    )
                },
                description = R.string.difficulty_beginner,
                onClick = {},
                imageAlignment = Alignment.TopEnd
            )
            ScribbleDashDifficultyLevelButton(
                image = {
                    Image(
                        painter = painterResource(R.drawable.ic_challenging),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                description = R.string.difficulty_challenging,
                onClick = {},
                modifier = Modifier.offset(y = (-16).dp)
            )
            ScribbleDashDifficultyLevelButton(
                image = {
                    Image(
                        painter = painterResource(R.drawable.ic_master),
                        contentDescription = null,
                    )
                },
                description = R.string.difficulty_master,
                onClick = {}
            )
        }
    }
}

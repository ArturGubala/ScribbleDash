package com.scribbledash.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashGameModeCard(
    @DrawableRes image: Int,
    @StringRes description: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                ambientColor = Color(0x1F726558),
                spotColor = Color(0x1F726558)
            )
            .background(
                color = Color(0xFF0DD280),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(all = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color(0xBF12B974),
                    spotColor = Color(0xBF12B974)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 22.dp, top = 26.dp, bottom = 26.dp)
                ) {
                    Text(
                        text = stringResource(description),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScribbleDashGameModeButtonPreview() {
    ScribbleDashTheme {
        ScribbleDashGameModeCard(
            image = R.drawable.ic_one_round_wonder,
            description = R.string.one_round_wonder,
            onClick = {}
        )
    }
}

package com.scribbledash.gameplay.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashButton(
    description: String,
    onClick: () -> Unit,
    buttonColor: Color,
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    val innerBoxBackgroundColor = buttonColor
    val textOpacity = if (isActive) 1f else .8f

    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = Color(0x1F726558),
                spotColor = Color(0x1F726558)
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(all = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = innerBoxBackgroundColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .then(
                    if (isActive) {
                        Modifier.clickable( onClick = onClick )
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .alpha(textOpacity)
            )
        }
    }
}

@Preview
@Composable
fun ScribbleDashButtonPreview() {
    ScribbleDashTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScribbleDashButton(
                description = stringResource(R.string.done),
                onClick = {},
                buttonColor = Color(0xFF0DD280),
                modifier = Modifier
                    .width(201.dp)
                    .height(64.dp)
            )
            ScribbleDashButton(
                description = stringResource(R.string.done),
                onClick = {},
                buttonColor = Color(0xFF238CFF),
                modifier = Modifier
                    .width(201.dp)
                    .height(64.dp)
            )
            ScribbleDashButton(
                description = stringResource(R.string.done),
                onClick = {},
                buttonColor = Color(0xFFE1D5CA),
                modifier = Modifier
                    .width(201.dp)
                    .height(64.dp),
                isActive = false
            )
        }
    }
}

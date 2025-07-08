package com.scribbledash.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.gameplay.components.TimerDisplay
import com.scribbledash.ui.theme.ScribbleDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScribbleDashTopAppBar(
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
    centerContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (RowScope.() -> Unit) = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(72.dp)
            .let {
                if (leadingContent != null || centerContent != null) {
                    it.padding(horizontal = 16.dp)
                } else {
                    it.padding(horizontal = 16.dp, vertical = 21.dp)
                }
            }
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (leadingContent != null || centerContent != null) {
                Arrangement.SpaceBetween
            } else {
                Arrangement.End
            }
        ) {
            leadingContent?.invoke()
            centerContent?.invoke()
            trailingContent()
        }
    }
}

@Preview
@Composable
fun ScribbleDashTopAppBarPreview() {
    ScribbleDashTheme {
        ScribbleDashTopAppBar(
            leadingContent = {

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
            },
            trailingContent = {
                IconButton(
                    onClick = {  }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_cross),
                        contentDescription = null,
                        tint = Color(0xFFA5978A)
                    )
                }
            }
        )
    }
}

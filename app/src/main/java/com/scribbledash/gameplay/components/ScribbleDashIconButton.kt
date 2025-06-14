package com.scribbledash.gameplay.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFEEE7E0),
                shape = RoundedCornerShape(22.dp)
            )
            .size(64.dp)
            .clip(RoundedCornerShape(22.dp))
            .then(
                if (isActive) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier.alpha(.4f)
                }
            )
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = icon
            ),
            contentDescription = null,
            tint = Color(0xFF514437)
        )
    }
}

@Preview
@Composable
fun ScribbleDashIconButtonPreview() {
    ScribbleDashTheme {
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ScribbleDashIconButton(
                icon = R.drawable.ic_reply,
                onClick = {}
            )
            ScribbleDashIconButton(
                icon = R.drawable.ic_forward,
                onClick = {},
                isActive = false
            )
        }
    }
}

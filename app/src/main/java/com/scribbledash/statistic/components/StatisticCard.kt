package com.scribbledash.statistic.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun StatisticCard(
    leadingIcon: @Composable (() -> Unit),
    trackedDescription: String,
    trackedValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x1F726558),
                spotColor = Color(0x1F726558)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.spacedBy(12.dp)
    ) {
        leadingIcon()
        Text(
            text = trackedDescription,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = trackedValue,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.width(110.dp)
        )
    }
}

@Composable
fun StatisticCardIcon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(52.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun StatisticCardPreview() {
    ScribbleDashTheme {
        StatisticCard(
            leadingIcon = {
                StatisticCardIcon(
                    icon = R.drawable.ic_hourglass,
                    modifier = Modifier
                        .background(
                            color = Color(0x1F742EFC),
                            shape = RoundedCornerShape(12.dp)

                        )
                )
            },
            trackedDescription = "Nothing to track...for now",
            trackedValue = "1234%"
        )
    }
}

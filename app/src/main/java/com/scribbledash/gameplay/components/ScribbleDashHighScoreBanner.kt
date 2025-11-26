package com.scribbledash.gameplay.components

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashHighScoreBanner() {
    Box(
        modifier = Modifier.width(150.dp).height(34.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(134.dp)
                .height(30.dp)
                .background(
                    color = Color(color = 0xFFFFDA35),
                    shape = RoundedCornerShape(50.dp)
                )
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(50.dp),
                    color = Color.White
                )
        )
        Image(
            painter = painterResource(R.drawable.ic_high_score),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
//                .offset(x = (-10).dp)
        )
    }
}

@Preview
@Composable
private fun ScribbleDashHighScoreBannerPreview() {
    ScribbleDashTheme {
        ScribbleDashHighScoreBanner()
    }
}

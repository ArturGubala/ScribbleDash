package com.scribbledash.gameplay.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashDrawingCounter(
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(28.dp)
                    .background(
                        color = Color(color = 0xFFEEE7E0),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .align(alignment = Alignment.CenterEnd)
                    .offset(x = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    modifier = Modifier,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = Color(0xFF514437)
                    )
                )

            }
            Image(
                painter = painterResource(R.drawable.ic_palette),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            )
    }
}

@Preview
@Composable
fun ScribbleDashDrawingCounterPreview() {
    ScribbleDashTheme {
        ScribbleDashDrawingCounter(
            value = "0",
            modifier = Modifier
                .width(76.dp)
        )
    }
}

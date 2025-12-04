package com.scribbledash.gameplay.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashDrawingCounter(
    value: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(color = 0xFFEEE7E0),
    textColor: Color = Color(0xFF514437),
    @DrawableRes icon: Int = R.drawable.ic_palette,
    isHighScore: Boolean = false

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(32.dp)
                    .background(
                        color = backgroundColor,
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
                        color = textColor
                    )
                )
            }
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
            )
        }
        if (isHighScore) {
            Text(
                text = stringResource(R.string.new_high),
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFA5978A)
            )
        }
    }
}

@Preview
@Composable
fun ScribbleDashDrawingCounterPreview() {
    ScribbleDashTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScribbleDashDrawingCounter(
                value = "0",
                modifier = Modifier
                    .width(76.dp)
            )

            ScribbleDashDrawingCounter(
                value = "0",
                modifier = Modifier
                    .width(78.dp),
                backgroundColor = Color(color = 0xFFED6363),
                textColor = Color.White,
                icon = R.drawable.ic_palette_outlined
            )

            ScribbleDashDrawingCounter(
                value = "0",
                modifier = Modifier
                    .width(78.dp),
                backgroundColor = Color(color = 0xFFED6363),
                textColor = Color.White,
                icon = R.drawable.ic_palette_outlined,
                isHighScore = true
            )
        }
    }
}

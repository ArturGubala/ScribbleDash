package com.scribbledash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.scribbledash.R

val BagelFatOne = FontFamily(
    Font(
        resId = R.font.bagel_fat_one_regular,
        weight = FontWeight.Normal
    )
)

val Outfit = FontFamily(
    Font(
        resId = R.font.outfit_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.outfit_medium,
        weight = FontWeight.Medium
    )
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        color = Text
    ),
    titleLarge= TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        color = Text
    ),
    bodyMedium = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Text2
    ),
    labelMedium = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Text2
    )
)

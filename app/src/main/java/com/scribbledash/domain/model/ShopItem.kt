package com.scribbledash.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.core.presentation.utils.ShopItemType

data class ShopItem(
    val id: String,
    val category: ShopItemTier,
    val type: ShopItemType,
    val previewColor: Color? = null,
    val previewBackgroundColor: Color? = null,
    // I know that domain should be pure kotlin but...
    @param:DrawableRes val previewBackgroundImage: Int? = null,
    val isPurchased: Boolean = false,
    val isSelected: Boolean = false,
    val cost: Int = 0,
    val isGradient: Boolean = false,
    val gradientColors: List<Color>? = null
)

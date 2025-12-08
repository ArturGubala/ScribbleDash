package com.scribbledash.domain.model

import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.ShopItemTier

data class ShopItem(
    val id: String,
    val category: ShopItemTier,
    val previewColor: Color? = null,
    val previewBackground: Color? = null,
    val isPurchased: Boolean = false,
    val cost: Int = 0
)

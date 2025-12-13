package com.scribbledash.data.local.mapper

import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.data.local.entity.ShopItemEntity
import com.scribbledash.domain.model.ShopItem
import androidx.core.graphics.toColorInt

fun ShopItemEntity.toShopItem(): ShopItem {
    val itemType = ShopItemType.valueOf(type)
    val itemTier = ShopItemTier.valueOf(tier)

    // Parse gradient colors if present
    val parsedGradientColors = if (isGradient && gradientColors != null) {
        parseGradientColors(gradientColors)
    } else {
        null
    }

    return when (itemType) {
        ShopItemType.PEN_COLOR -> {
            ShopItem(
                id = id,
                category = itemTier,
                type = itemType,
                cost = cost,
                isPurchased = isPurchased,
                isSelected = isSelected,
                previewColor = if (isGradient) null else parseColor(colorHex), // No solid color if gradient
                isGradient = isGradient,
                gradientColors = parsedGradientColors
            )
        }
        ShopItemType.CANVAS_BACKGROUND -> {
            ShopItem(
                id = id,
                category = itemTier,
                type = itemType,
                cost = cost,
                isPurchased = isPurchased,
                isSelected = isSelected,
                previewBackgroundColor = if (previewBackgroundImageRes == null) parseColor(colorHex) else null,
                previewBackgroundImage = previewBackgroundImageRes
            )
        }
    }
}

private fun parseColor(hexColor: String): Color {
    return try {
        Color(hexColor.toColorInt())
    } catch (e: Exception) {
        Color.Black
    }
}

private fun parseGradientColors(gradientColorsString: String): List<Color> {
    return try {
        gradientColorsString
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { parseColor(it) }
    } catch (e: Exception) {
        emptyList()
    }
}

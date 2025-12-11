package com.scribbledash.data.local.mapper

import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.data.local.entity.ShopItemEntity
import com.scribbledash.domain.model.ShopItem
import androidx.core.graphics.toColorInt

fun ShopItemEntity.toShopItem(): ShopItem {
    return ShopItem(
        id = id,
        category = ShopItemTier.valueOf(tier),
        type = ShopItemType.valueOf(type),
        previewColor = if (type == ShopItemType.PEN_COLOR.name) {
            Color(colorHex.toColorInt())
        } else null,
        previewBackgroundColor = if (type == ShopItemType.CANVAS_BACKGROUND.name && previewBackgroundImageRes == null) {
            Color(colorHex.toColorInt())
        } else null,
        previewBackgroundImage = previewBackgroundImageRes,
        isPurchased = isPurchased,
        isSelected = isSelected,
        cost = cost
    )
}

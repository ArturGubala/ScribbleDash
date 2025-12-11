package com.scribbledash.shop

import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.domain.model.ShopItem

data class ShopState(
    val coins: Int = 0,
    val selectedTab: ShopItemType = ShopItemType.PEN_COLOR,
    val penItems: List<ShopItem> = emptyList(),
    val canvasItems: List<ShopItem> = emptyList(),
    val isLoading: Boolean = false
)

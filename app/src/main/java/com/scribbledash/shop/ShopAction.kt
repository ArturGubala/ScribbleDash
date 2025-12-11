package com.scribbledash.shop

import com.scribbledash.core.presentation.utils.ShopItemType

sealed interface ShopAction {
    data class OnTabSelected(val tab: ShopItemType) : ShopAction
    data class OnItemClicked(val itemId: String) : ShopAction
}

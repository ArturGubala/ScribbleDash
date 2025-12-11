package com.scribbledash.shop

sealed interface ShopEvents {
    data class ShowError(val message: String) : ShopEvents
    data object ItemPurchased : ShopEvents
}

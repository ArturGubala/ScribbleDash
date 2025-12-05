package com.scribbledash.shop

interface ShopAction {
    data object OnTabClick : ShopAction
    data object OnItemClick : ShopAction
}

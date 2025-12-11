package com.scribbledash.domain.repository

import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.domain.model.ShopItem
import kotlinx.coroutines.flow.Flow

interface ShopRepository {

    fun getItemsByType(type: ShopItemType): Flow<List<ShopItem>>

    suspend fun getSelectedItemByType(type: ShopItemType): ShopItem?

    suspend fun purchaseItem(itemId: String, currentCoins: Int): Result<Unit>

    suspend fun selectItem(itemId: String, type: ShopItemType)
}

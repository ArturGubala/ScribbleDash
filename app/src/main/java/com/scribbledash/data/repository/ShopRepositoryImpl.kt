package com.scribbledash.data.repository

import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.data.local.dao.ShopItemDao
import com.scribbledash.data.local.mapper.toShopItem
import com.scribbledash.domain.model.ShopItem
import com.scribbledash.domain.repository.ShopRepository
import com.scribbledash.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShopRepositoryImpl(
    private val shopItemDao: ShopItemDao,
    private val walletRepository: WalletRepository
) : ShopRepository {

    override fun getItemsByType(type: ShopItemType): Flow<List<ShopItem>> {
        return shopItemDao.getItemsByType(type.name)
            .map { entities -> entities.map { it.toShopItem() } }
    }

    override suspend fun getSelectedItemByType(type: ShopItemType): ShopItem? {
        return shopItemDao.getSelectedItemByType(type.name)?.toShopItem()
    }

    override suspend fun purchaseItem(itemId: String, currentCoins: Int): Result<Unit> {
        return try {
            val item = shopItemDao.getItemById(itemId)
                ?: return Result.failure(Exception("Item not found"))

            if (item.isPurchased) {
                return Result.failure(Exception("Item already purchased"))
            }

            if (currentCoins < item.cost) {
                return Result.failure(Exception("Not enough coins"))
            }

            // Deduct coins using WalletRepository
            walletRepository.deductCoins(item.cost)

            // Purchase and select item
            shopItemDao.purchaseItem(itemId, item.type)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun selectItem(itemId: String, type: ShopItemType) {
        shopItemDao.selectItem(itemId, type.name)
    }
}

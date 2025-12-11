package com.scribbledash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.scribbledash.data.local.entity.ShopItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items WHERE type = :type")
    fun getItemsByType(type: String): Flow<List<ShopItemEntity>>

    @Query("SELECT * FROM shop_items WHERE type = :type AND isSelected = 1 LIMIT 1")
    suspend fun getSelectedItemByType(type: String): ShopItemEntity?

    @Query("SELECT * FROM shop_items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ShopItemEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<ShopItemEntity>)

    @Transaction
    suspend fun selectItem(itemId: String, type: String) {
        deselectAllItemsOfType(type)
        selectItemById(itemId)
    }

    @Query("UPDATE shop_items SET isSelected = 0 WHERE type = :type")
    suspend fun deselectAllItemsOfType(type: String)

    @Query("UPDATE shop_items SET isSelected = 1 WHERE id = :itemId")
    suspend fun selectItemById(itemId: String)

    @Transaction
    suspend fun purchaseItem(itemId: String, type: String) {
        markAsPurchased(itemId)
        selectItem(itemId, type)
    }

    @Query("UPDATE shop_items SET isPurchased = 1 WHERE id = :itemId")
    suspend fun markAsPurchased(itemId: String)
}

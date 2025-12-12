package com.scribbledash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scribbledash.data.local.entity.WalletEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallet WHERE id = 1")
    suspend fun getWallet(): WalletEntity?

    @Query("SELECT coins FROM wallet WHERE id = 1")
    suspend fun getCoins(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: WalletEntity)

    @Query("UPDATE wallet SET coins = coins + :amount WHERE id = 1")
    suspend fun addCoins(amount: Int)

    @Query("UPDATE wallet SET coins = coins - :amount WHERE id = 1")
    suspend fun deductCoins(amount: Int)

    @Query("SELECT * FROM wallet WHERE id = 1")
    fun observeWallet(): Flow<WalletEntity?>

    @Query("UPDATE wallet SET coins = :amount WHERE id = 1")
    suspend fun setCoins(amount: Int)

    // Helper function to initialize wallet if it doesn't exist
    suspend fun ensureWalletExists() {
        if (getCoins() == null) {
            insertWallet(WalletEntity(id = 1, coins = 0))
        }
    }
}

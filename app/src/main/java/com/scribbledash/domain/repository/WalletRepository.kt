package com.scribbledash.domain.repository

import com.scribbledash.domain.model.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun getWallet(): Wallet

    suspend fun getCoins(): Int

    fun observeCoins(): Flow<Int>

    suspend fun addCoins(amount: Int)

    suspend fun deductCoins(amount: Int)

    suspend fun setCoins(amount: Int)
}

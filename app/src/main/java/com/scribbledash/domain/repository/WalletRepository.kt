package com.scribbledash.domain.repository

import com.scribbledash.domain.model.Wallet

interface WalletRepository {

    suspend fun getWallet(): Wallet

    suspend fun getCoins(): Int

    suspend fun addCoins(amount: Int)

    suspend fun deductCoins(amount: Int)

    suspend fun setCoins(amount: Int)
}

package com.scribbledash.data.repository

import com.scribbledash.data.local.dao.WalletDao
import com.scribbledash.data.local.mapper.toWallet
import com.scribbledash.domain.model.Wallet
import com.scribbledash.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WalletRepositoryImpl(private val dao: WalletDao) : WalletRepository {
    override suspend fun getWallet(): Wallet {
        return dao.getWallet().let { walletEntity ->
            walletEntity?.toWallet() ?: Wallet(coins = 0)
        }
    }

    override suspend fun getCoins(): Int {
        dao.ensureWalletExists()
        return dao.getCoins() ?: 0
    }

    override fun observeCoins(): Flow<Int> {
        return dao.observeWallet()
            .map { it?.coins ?: 0 }
    }

    override suspend fun addCoins(amount: Int) {
        dao.ensureWalletExists()
        dao.addCoins(amount)
    }

    override suspend fun deductCoins(amount: Int) {
        dao.ensureWalletExists()
        dao.deductCoins(amount)
    }

    override suspend fun setCoins(amount: Int) {
        dao.ensureWalletExists()
        dao.setCoins(amount)
    }
}

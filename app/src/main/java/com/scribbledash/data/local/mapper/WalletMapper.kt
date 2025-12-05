package com.scribbledash.data.local.mapper

import com.scribbledash.data.local.entity.WalletEntity
import com.scribbledash.domain.model.Wallet

fun WalletEntity.toWallet(): Wallet {
    return Wallet(
        coins = coins
    )
}

fun Wallet.toWalletEntity(): WalletEntity {
    return WalletEntity(
        id = 1,
        coins = coins
    )
}

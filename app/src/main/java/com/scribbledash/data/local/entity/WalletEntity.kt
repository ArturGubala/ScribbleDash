package com.scribbledash.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class WalletEntity(
    @PrimaryKey
    val id: Int = 1,
    val coins: Int = 0
)

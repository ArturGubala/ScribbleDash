package com.scribbledash.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: String,
    val tier: String,
    val colorHex: String,
    val cost: Int,
    val isDefault: Boolean,
    val isPurchased: Boolean,
    val isSelected: Boolean,
    val previewBackgroundImageRes: Int? = null
)

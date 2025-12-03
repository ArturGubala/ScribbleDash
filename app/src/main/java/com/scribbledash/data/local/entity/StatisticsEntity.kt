package com.scribbledash.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "statistics",
    indices = [Index(value = ["gameMode", "type"], unique = true)]
)
data class StatisticsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val gameMode: String,
    val type: String,
    val descriptionResName: String,
    val value: Float,
    val displayOrder: UShort,
    val iconResName: String
)

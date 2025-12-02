package com.scribbledash.data.local.converter

import androidx.room.TypeConverter

class UShortConverter {
    @TypeConverter
    fun fromUShort(value: UShort?): Int? {
        return value?.toInt()
    }

    @TypeConverter
    fun toUShort(value: Int?): UShort? {
        return value?.toUShort()
    }
}

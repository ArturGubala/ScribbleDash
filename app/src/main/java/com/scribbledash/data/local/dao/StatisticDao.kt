package com.scribbledash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scribbledash.data.local.entity.StatisticEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {

    @Query("SELECT * FROM statistics ORDER BY displayOrder ASC")
    fun getAllStatistics(): Flow<List<StatisticEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(statistics: List<StatisticEntity>)
}

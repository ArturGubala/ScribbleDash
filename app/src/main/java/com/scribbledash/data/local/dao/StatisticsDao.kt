package com.scribbledash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.scribbledash.data.local.entity.StatisticsEntity

@Dao
interface StatisticDao {

    @Query("SELECT * FROM statistics ORDER BY displayOrder ASC")
    fun getAllStatistics(): List<StatisticsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(statistics: List<StatisticsEntity>)

    @Update
    suspend fun updateStatistics(statistic: StatisticsEntity)

    @Query("SELECT * FROM statistics WHERE gameMode = :gameMode AND type = :type")
    suspend fun getStatistics(gameMode: String, type: String): StatisticsEntity?
}

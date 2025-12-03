package com.scribbledash.domain.repository

import com.scribbledash.domain.model.Statistics

interface StatisticsRepository {
    suspend fun getStatistics(): List<Statistics>
    suspend fun getStatistics(gameMode: String, type: String): Statistics?
    suspend fun updateStatistics(statistic: Statistics)
}

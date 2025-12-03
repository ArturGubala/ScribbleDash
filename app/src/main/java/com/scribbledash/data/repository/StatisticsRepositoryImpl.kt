package com.scribbledash.data.repository

import com.scribbledash.data.local.dao.StatisticDao
import com.scribbledash.data.local.mapper.toStatistics
import com.scribbledash.data.local.mapper.toEntity
import com.scribbledash.domain.model.Statistics
import com.scribbledash.domain.repository.StatisticsRepository

class StatisticsRepositoryImpl(private val dao: StatisticDao) : StatisticsRepository {
    override suspend fun getStatistics(): List<Statistics> {
        return dao.getAllStatistics().map { it.toStatistics() }
    }

    override suspend fun getStatistics(gameMode: String, type: String): Statistics? {
        return dao.getStatistics(gameMode, type)?.toStatistics()
    }

    override suspend fun updateStatistics(statistic: Statistics) {
        dao.updateStatistics(statistic.toEntity())
    }
}

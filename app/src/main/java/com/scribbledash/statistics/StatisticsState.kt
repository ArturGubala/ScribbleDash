package com.scribbledash.statistics

import com.scribbledash.domain.model.Statistics

data class StatisticsState(
    val statistics: List<Statistics> = emptyList()
)

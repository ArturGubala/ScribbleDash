package com.scribbledash.data.local.mapper

import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.StatisticsType
import com.scribbledash.data.local.entity.StatisticEntity
import com.scribbledash.domain.model.Statistic

fun StatisticEntity.toStatistics(): Statistic {
    return Statistic(
        id = id,
        gameMode = GameType.valueOf(gameMode),
        type = StatisticsType.valueOf(type),
        descriptionResName = descriptionResName,
        value = value,
        displayOrder = displayOrder,
        iconResName = iconResName
    )
}

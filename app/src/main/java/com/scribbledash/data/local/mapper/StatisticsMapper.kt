package com.scribbledash.data.local.mapper

import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.StatisticsType
import com.scribbledash.data.local.entity.StatisticsEntity
import com.scribbledash.domain.model.Statistics

fun StatisticsEntity.toStatistics(): Statistics {
    return Statistics(
        id = id,
        gameMode = GameType.valueOf(gameMode),
        type = StatisticsType.valueOf(type),
        descriptionResName = descriptionResName,
        value = value,
        displayOrder = displayOrder,
        iconResName = iconResName
    )
}

fun Statistics.toEntity(): StatisticsEntity {
    return StatisticsEntity(
        id = id,
        gameMode = gameMode.name,
        type = type.name,
        descriptionResName = descriptionResName,
        value = value,
        displayOrder = displayOrder,
        iconResName = iconResName
    )
}

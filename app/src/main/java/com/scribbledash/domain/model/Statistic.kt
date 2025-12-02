package com.scribbledash.domain.model

import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.StatisticsType

data class Statistic(
    val id: Int,
    val gameMode: GameType,
    val type: StatisticsType,
    val descriptionResName: String,
    val value: Float,
    val displayOrder: UShort,
    val iconResName: String
)

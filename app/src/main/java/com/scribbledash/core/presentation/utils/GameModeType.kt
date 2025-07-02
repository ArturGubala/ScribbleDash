package com.scribbledash.core.presentation.utils

enum class GameType {
    ONE_ROUND_WONDER,
    SPEED_DRAW,
    ENDLESS_MODE;

    fun getDisplayName(): String = when (this) {
        ONE_ROUND_WONDER -> "One Round Wonder"
        SPEED_DRAW -> "Speed Draw"
        ENDLESS_MODE -> "Endless Mode"
    }
}

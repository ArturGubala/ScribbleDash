package com.scribbledash.core.presentation.utils

enum class DifficultyLevel {
    BEGINNER,
    CHALLENGING,
    MASTER;

    fun getDisplayName(): String = when (this) {
        BEGINNER -> "Beginner"
        CHALLENGING -> "Challenging"
        MASTER -> "Master"
    }
}

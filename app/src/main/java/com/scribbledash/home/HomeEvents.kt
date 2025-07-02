package com.scribbledash.home

import com.scribbledash.core.presentation.utils.GameType

interface HomeEvents {
    data class NavigateToDifficultyLevel(val gameType: GameType) : HomeEvents
}

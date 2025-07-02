package com.scribbledash.home

import com.scribbledash.core.presentation.utils.GameType

interface HomeAction {
    data class OnGameModeTypeClick(val gameType: GameType) : HomeAction
}

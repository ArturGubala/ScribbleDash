package com.scribbledash.home

interface HomeAction {
    data class OnGameModeTypeClick(val finalDestination: String) : HomeAction
}

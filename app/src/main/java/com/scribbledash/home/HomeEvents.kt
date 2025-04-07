package com.scribbledash.home

interface HomeEvents {
    data class NavigateToDifficultyLevel(val finalDestination: String) : HomeEvents
}

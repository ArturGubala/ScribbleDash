package com.scribbledash.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {

    private val eventChannel = Channel<HomeEvents>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnGameModeTypeClick -> {
                viewModelScope.launch {
                    eventChannel.send(HomeEvents.NavigateToDifficultyLevel(gameType = action.gameType))
                }
            }
        }
    }
}

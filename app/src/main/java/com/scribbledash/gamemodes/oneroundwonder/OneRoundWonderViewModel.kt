package com.scribbledash.gamemodes.oneroundwonder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OneRoundWonderViewModel(): ViewModel() {

    private val eventChannel = Channel<OneRoundWonderEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: OneRoundWonderAction) {
        when(action) {
            is OneRoundWonderAction.OnBackClicked -> {
                viewModelScope.launch {
                    eventChannel.send(OneRoundWonderEvent.NavigateBackToHome)
                }
            }
        }
    }
}

package com.scribbledash.core.presentation.screens.difficultylevel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.gameplay.presentation.GameplayEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DifficultyLevelViewModel(): ViewModel() {

    private val eventChannel = Channel<DifficultyLevelEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: DifficultyLevelAction) {
        when(action) {
            is DifficultyLevelAction.OnDifficultyLevelClick -> onDifficultyLevelClick()
            is DifficultyLevelAction.OnBackClicked -> onBackClicked()
        }
    }

    private fun onDifficultyLevelClick() {
        viewModelScope.launch {
            eventChannel.send(DifficultyLevelEvent.NavigateToDestination)
        }
    }

    private fun onBackClicked() {
        viewModelScope.launch {
            eventChannel.send(DifficultyLevelEvent.NavigateBackToHome)
        }
    }
}

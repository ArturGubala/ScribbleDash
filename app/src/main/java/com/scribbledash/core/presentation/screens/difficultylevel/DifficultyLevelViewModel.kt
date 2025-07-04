package com.scribbledash.core.presentation.screens.difficultylevel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.core.presentation.utils.DifficultyLevel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DifficultyLevelViewModel(): ViewModel() {

    private val eventChannel = Channel<DifficultyLevelEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: DifficultyLevelAction) {
        when(action) {
            is DifficultyLevelAction.OnDifficultyLevelClick -> onDifficultyLevelClick(action.difficultyLevel)
            is DifficultyLevelAction.OnBackClicked -> onBackClicked()
        }
    }

    private fun onDifficultyLevelClick(difficultyLevel: DifficultyLevel) {
        viewModelScope.launch {
            eventChannel.send(DifficultyLevelEvent.NavigateToGameplay(difficultyLevel = difficultyLevel))
        }
    }

    private fun onBackClicked() {
        viewModelScope.launch {
            eventChannel.send(DifficultyLevelEvent.NavigateBackToHome)
        }
    }
}

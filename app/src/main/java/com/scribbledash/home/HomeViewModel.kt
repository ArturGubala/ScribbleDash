package com.scribbledash.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.domain.repository.WalletRepository
import com.scribbledash.gameplay.presentation.GameplayState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val walletRepository: WalletRepository
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            HomeState()
        )

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

    private fun loadCoins() {
        viewModelScope.launch {
            val coins = walletRepository.getCoins()
            _state.update {
                it.copy(
                    coins = coins
                )
            }
        }
    }
}

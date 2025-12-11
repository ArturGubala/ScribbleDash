package com.scribbledash.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.domain.repository.ShopRepository
import com.scribbledash.domain.repository.WalletRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel(
    private val shopRepository: ShopRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ShopState())
    val state = _state
        .onStart {
            loadWallet()
            observeShopItems()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ShopState()
        )

    private val eventChannel = Channel<ShopEvents>()
    val events = eventChannel.receiveAsFlow()

    /**
     * Load wallet coins once
     */
    private fun loadWallet() {
        viewModelScope.launch {
            try {
                val coins = walletRepository.getCoins()
                _state.update { it.copy(coins = coins) }
            } catch (e: Exception) {
                eventChannel.send(ShopEvents.ShowError("Failed to load coins"))
            }
        }
    }

    /**
     * Observe shop items for reactive updates
     */
    private fun observeShopItems() {
        // Observe pen items
        shopRepository.getItemsByType(ShopItemType.PEN_COLOR)
            .onEach { items ->
                _state.update { it.copy(penItems = items) }
            }
            .launchIn(viewModelScope)

        // Observe canvas items
        shopRepository.getItemsByType(ShopItemType.CANVAS_BACKGROUND)
            .onEach { items ->
                _state.update { it.copy(canvasItems = items) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ShopAction) {
        when (action) {
            is ShopAction.OnTabSelected -> handleTabSelection(action.tab)
            is ShopAction.OnItemClicked -> handleItemClick(action.itemId)
        }
    }

    /**
     * Handle tab selection between Pen and Canvas
     */
    private fun handleTabSelection(tab: ShopItemType) {
        _state.update { it.copy(selectedTab = tab) }
    }

    /**
     * Handle item click - purchase or select
     */
    private fun handleItemClick(itemId: String) {
        viewModelScope.launch {
            val currentState = _state.value

            // Find the item
            val item = (currentState.penItems + currentState.canvasItems)
                .firstOrNull { it.id == itemId }

            if (item == null) {
                eventChannel.send(ShopEvents.ShowError("Item not found"))
                return@launch
            }

            // Check if already purchased
            if (item.isPurchased) {
                // Just select it
                selectItem(itemId, item.type)
            } else {
                // Try to purchase it
                purchaseItem(itemId, item.cost, currentState.coins)
            }
        }
    }

    /**
     * Purchase an item with coins
     */
    private suspend fun purchaseItem(itemId: String, itemCost: Int, currentCoins: Int) {
        _state.update { it.copy(isLoading = true) }

        try {
            val result = shopRepository.purchaseItem(itemId, currentCoins)

            result.fold(
                onSuccess = {
                    // Update local coins state (optimistic update)
                    _state.update { it.copy(coins = currentCoins - itemCost) }
                    eventChannel.send(ShopEvents.ItemPurchased)
                },
                onFailure = { exception ->
                    val errorMessage = when {
                        exception.message?.contains(
                            "Not enough coins",
                            ignoreCase = true
                        ) == true ->
                            "Not enough coins to purchase this item"

                        exception.message?.contains(
                            "already purchased",
                            ignoreCase = true
                        ) == true ->
                            "You already own this item"

                        exception.message?.contains("not found", ignoreCase = true) == true ->
                            "Item not found"

                        else ->
                            "Failed to purchase item"
                    }

                    eventChannel.send(ShopEvents.ShowError(message = errorMessage))
                }
            )
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

    /**
     * Select an already purchased item
     */
    private suspend fun selectItem(itemId: String, type: ShopItemType) {
        try {
            shopRepository.selectItem(itemId, type)
        } catch (e: Exception) {
            eventChannel.send(ShopEvents.ShowError("Failed to select item: ${e.message}"))
        }
    }
}

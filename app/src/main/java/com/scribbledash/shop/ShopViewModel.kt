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
            observeWallet()
            observeShopItems()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ShopState()
        )

    private val eventChannel = Channel<ShopEvents>()
    val events = eventChannel.receiveAsFlow()

    private fun observeWallet() {
        walletRepository.observeCoins()
            .onEach { coins ->
                _state.update { it.copy(coins = coins) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeShopItems() {
        shopRepository.getItemsByType(ShopItemType.PEN_COLOR)
            .onEach { items ->
                _state.update { it.copy(penItems = items) }
            }
            .launchIn(viewModelScope)
        
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

    private fun handleTabSelection(tab: ShopItemType) {
        _state.update { it.copy(selectedTab = tab) }
    }

    private fun handleItemClick(itemId: String) {
        viewModelScope.launch {
            val currentState = _state.value
            val item = currentState.allItems.firstOrNull { it.id == itemId }
                ?: run {
                    eventChannel.send(ShopEvents.ShowError("Item not found"))
                    return@launch
                }

            if (item.isPurchased) {
                selectItem(itemId, item.type)
            } else {
                purchaseItem(itemId, item.cost, currentState.coins)
            }
        }
    }

    private suspend fun purchaseItem(itemId: String, itemCost: Int, currentCoins: Int) {
        _state.update { it.copy(purchasingItemId = itemId) }

        try {
            val result = shopRepository.purchaseItem(itemId, currentCoins)

            result.fold(
                onSuccess = {
                    eventChannel.send(ShopEvents.ItemPurchased)
                },
                onFailure = { exception ->
                    eventChannel.send(ShopEvents.ShowError(getPurchaseErrorMessage(exception)))
                }
            )
        } finally {
            _state.update { it.copy(purchasingItemId = null) }
        }
    }

    private suspend fun selectItem(itemId: String, type: ShopItemType) {
        try {
            shopRepository.selectItem(itemId, type)
        } catch (e: Exception) {
            eventChannel.send(ShopEvents.ShowError("Failed to select item: ${e.message}"))
        }
    }

    private fun getPurchaseErrorMessage(exception: Throwable): String {
        return when {
            exception.message?.contains("Not enough coins", ignoreCase = true) == true ->
                "Not enough coins to purchase this item"
            exception.message?.contains("already purchased", ignoreCase = true) == true ->
                "You already own this item"
            exception.message?.contains("not found", ignoreCase = true) == true ->
                "Item not found"
            else ->
                "Failed to purchase item"
        }
    }
}

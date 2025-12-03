package com.scribbledash.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scribbledash.domain.repository.StatisticsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatisticsViewModel(private val statisticRepository: StatisticsRepository) : ViewModel() {

    private val _state = MutableStateFlow(StatisticsState())
    val state = _state.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            val statistics = statisticRepository.getStatistics()
            _state.update { it.copy(statistics = statistics) }
        }
    }
}

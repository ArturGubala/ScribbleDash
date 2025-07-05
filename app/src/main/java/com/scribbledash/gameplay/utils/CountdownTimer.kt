package com.scribbledash.gameplay.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CountdownTimer(
    private val coroutineScope: CoroutineScope,
    private val onTick: (Long) -> Unit = {},
    private val onFinished: () -> Unit = {}
) {
    private var countdownJob: Job? = null
    private var _timeRemaining: Long = 0L
    private var _isRunning: Boolean = false

    val timeRemaining: Long get() = _timeRemaining
    val isRunning: Boolean get() = _isRunning

    fun start(durationMs: Long) {
        stop()
        _timeRemaining = durationMs
        _isRunning = true

        countdownJob = coroutineScope.launch {
            while (_isRunning && _timeRemaining > 0) {
                onTick(_timeRemaining)
                delay(1000L)
                _timeRemaining -= 1000L
            }
            _isRunning = false
            if (_timeRemaining <= 0) {
                onFinished()
            }
        }
    }

    fun pause() {
        _isRunning = false
        countdownJob?.cancel()
    }

    fun resume() {
        if (_timeRemaining > 0) {
            start(_timeRemaining)
        }
    }

    fun stop() {
        _isRunning = false
        countdownJob?.cancel()
        _timeRemaining = 0L
    }
}

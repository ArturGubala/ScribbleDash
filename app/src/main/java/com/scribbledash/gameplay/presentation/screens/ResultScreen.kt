package com.scribbledash.gameplay.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.gameplay.presentation.GameplayState
import com.scribbledash.gameplay.presentation.GameplayViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ResultRoute(
    navController: NavController,
    viewModel: GameplayViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ResultScreen(state = state)
}

@Composable
private fun ResultScreen(
    state: GameplayState
) {
}

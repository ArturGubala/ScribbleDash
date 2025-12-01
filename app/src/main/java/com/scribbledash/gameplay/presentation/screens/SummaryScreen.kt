package com.scribbledash.gameplay.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashButton
import com.scribbledash.gameplay.components.ScribbleDashScoreCard
import com.scribbledash.gameplay.navigation.GAMEPLAY_GRAPH_ROUTE
import com.scribbledash.gameplay.navigation.navigateToGameplay
import com.scribbledash.gameplay.presentation.GameplayAction
import com.scribbledash.gameplay.presentation.GameplayEvent
import com.scribbledash.gameplay.presentation.GameplayState
import com.scribbledash.gameplay.presentation.GameplayViewModel
import com.scribbledash.gameplay.utils.sharedViewModel
import com.scribbledash.home.navigation.HomeScreen
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
internal fun SummaryRoute(
    navController: NavController,
) {
    val viewModel: GameplayViewModel = sharedViewModel(navController, GAMEPLAY_GRAPH_ROUTE)
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onAction(GameplayAction.OnBackClicked)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is GameplayEvent.NavigateBackToHome -> {
                navController.popBackStack(route = HomeScreen, inclusive = false)
            }

            is GameplayEvent.NavigateToGameplayScreen -> {
                navController.navigateToGameplay(
                    gameType = event.gameType,
                    difficultyLevel = event.difficultyLevel
                )
            }
        }
    }

    SummaryScreen(
        onBackClick = { viewModel.onAction(GameplayAction.OnBackClicked) },
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SummaryScreen(
    onBackClick: () -> Unit,
    state: GameplayState,
    onAction: (GameplayAction) -> Unit,
) {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                trailingContent = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_cross),
                            contentDescription = null,
                            tint = Color(0xFFA5978A)
                        )
                    }
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(Color(0xFFFEFAF6))
            .systemBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Time’s up!",
                style = MaterialTheme.typography.labelLarge
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                ScribbleDashScoreCard(
                    score = state.score,
                    drawings = state.drawingCounter,
                    feedbackTitle = "Meh",
                    feedbackDescription = "This is what happens when you let a cat hold the pencil!",
                    isHighScore = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            ScribbleDashButton(
                description = "DRAW AGAIN",
                onClick = {
                    when (state.gameType) {
                        GameType.ONE_ROUND_WONDER -> TODO()
                        GameType.SPEED_DRAW -> onAction(GameplayAction.OnDrawAgainClick)
                        GameType.ENDLESS_MODE -> TODO()
                    }
                },
                buttonColor = Color(0xFF238CFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                isActive = true
            )
        }
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    ScribbleDashTheme {
        SummaryScreen(
            onBackClick = {},
            state = GameplayState(),
            onAction = {}
        )
    }
}

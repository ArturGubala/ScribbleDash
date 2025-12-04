package com.scribbledash.gameplay.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.screens.difficultylevel.navigation.navigateToDifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashButton
import com.scribbledash.gameplay.components.ScribbleDashDrawingArea
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.computeBounds
import com.scribbledash.gameplay.model.toAndroidPath
import com.scribbledash.gameplay.navigation.GAMEPLAY_GRAPH_ROUTE
import com.scribbledash.gameplay.navigation.navigateToGameplay
import com.scribbledash.gameplay.navigation.navigateToSummary
import com.scribbledash.gameplay.presentation.GameplayAction
import com.scribbledash.gameplay.presentation.GameplayEvent
import com.scribbledash.gameplay.presentation.GameplayState
import com.scribbledash.gameplay.presentation.GameplayViewModel
import com.scribbledash.gameplay.utils.ScoreFeedback
import com.scribbledash.gameplay.utils.sharedViewModel
import com.scribbledash.home.navigation.HomeScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
internal fun ResultRoute(
    navController: NavController
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
            is GameplayEvent.NavigateToDifficultyLevelScreen -> {
                navController.navigateToDifficultyLevel(state.gameType)
            }
            is GameplayEvent.NavigateToGameplayScreen -> {
                navController.navigateToGameplay(gameType = event.gameType, difficultyLevel = event.difficultyLevel)
            }
            is GameplayEvent.NavigateToSummary -> {
                navController.navigateToSummary()
            }
        }
    }

    ResultScreen(
        onBackClick = { viewModel.onAction(GameplayAction.OnBackClicked) },
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ResultScreen(
    onBackClick: () -> Unit,
    state: GameplayState,
    onAction: (GameplayAction) -> Unit,
) {
    val score = when(state.gameType) {
        GameType.ONE_ROUND_WONDER,
        GameType.SPEED_DRAW -> state.finalScore.toInt()
        GameType.ENDLESS -> state.lastScore.toInt()
    }
    val randomFeedbackId = remember(state.finalScore) {
        ScoreFeedback.getRandomFeedback(score)
    }
    val scoreHeadline = remember(score) {
        ScoreFeedback.getScoreHeadline(score)
    }

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
                .padding(start = 16.dp, end = 16.dp, top = 84.dp, bottom = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val primaryButtonText = when (state.gameType) {
                GameType.ONE_ROUND_WONDER -> stringResource(R.string.try_again)
                GameType.SPEED_DRAW -> stringResource(R.string.draw_again)
                GameType.ENDLESS -> stringResource(R.string.finish)
            }

            ScribbleDashScreenTitle(
                headline = {
                    Text(
                        text = when (state.gameType) {
                            GameType.ONE_ROUND_WONDER,
                            GameType.SPEED_DRAW -> {
                                "${"%.0f".format(state.finalScore)}%"
                            }
                            GameType.ENDLESS -> {
                                "${"%.0f".format(state.lastScore)}%"
                            }
                        },
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 47.dp),
            )

            Box(contentAlignment = Alignment.BottomCenter) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier.rotate(-10f)
                    ) {
                        ScribbleDashDrawingArea(
                            currentPath = state.currentPath,
                            paths = state.paths,
                            exampleDrawing = state.previewDrawing,
                            onAction = onAction,
                            modifier = Modifier.size(160.dp),
                            canDrawing = false
                        )
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = 25.dp)
                            .rotate(10f)
                            .padding(bottom = 45.dp)
                    ) {
                        ScribbleDashDrawingArea(
                            currentPath = state.currentPath,
                            paths = state.paths,
                            exampleDrawing = ScribbleDashPath(state.paths.toAndroidPath(), state.paths.toAndroidPath().computeBounds(   )),
                            onAction = onAction,
                            modifier = Modifier.size(160.dp),
                            canDrawing = false
                        )
                    }

                }

                if (state.gameType == GameType.ENDLESS) {
                    if (state.lastScore >= 70) {
                        Image(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = "Check icon",
                            modifier = Modifier.size(84.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_cross),
                            contentDescription = "Check icon",
                            modifier = Modifier.size(84.dp)
                        )
                    }
                }
            }

            ScribbleDashScreenTitle(
                headline = {
                    Text(
                        text = scoreHeadline,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                subtitle = {
                    Text(
                        text = stringResource(randomFeedbackId),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            ScribbleDashButton(
                description = primaryButtonText,
                onClick = {
                    when(state.gameType) {
                        GameType.ONE_ROUND_WONDER -> onAction(GameplayAction.OnTryAgainClick(gameType = state.gameType))
                        GameType.SPEED_DRAW -> onAction(GameplayAction.OnDrawAgainClick)
                        GameType.ENDLESS -> onAction(GameplayAction.OnFinishClick)
                    }
                },
                buttonColor = Color(0xFF238CFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                isActive = true
            )
            if (state.gameType == GameType.ENDLESS && state.lastScore >= 70) {
                ScribbleDashButton(
                    description = stringResource(R.string.next_drawing),
                    onClick = {
                        when(state.gameType) {
                            GameType.ENDLESS -> onAction(GameplayAction.OnDrawAgainClick)
                            else -> { }
                        }
                    },
                    buttonColor = Color(0xFF0DD280),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    isActive = true
                )
            }
        }
    }
}

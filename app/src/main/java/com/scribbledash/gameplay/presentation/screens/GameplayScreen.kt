package com.scribbledash.gameplay.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.CountdownText
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashButton
import com.scribbledash.gameplay.components.ScribbleDashDrawingArea
import com.scribbledash.gameplay.components.ScribbleDashIconButton
import com.scribbledash.gameplay.navigation.navigateToResult
import com.scribbledash.gameplay.presentation.GameplayAction
import com.scribbledash.gameplay.presentation.GameplayEvent
import com.scribbledash.gameplay.presentation.GameplayState
import com.scribbledash.gameplay.presentation.GameplayViewModel
import com.scribbledash.gameplay.utils.sharedViewModel
import com.scribbledash.home.navigation.HomeScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
internal fun GameplayRoute(
    gameType: GameType,
    difficultyLevel: DifficultyLevel,
    navController: NavController
) {
    val viewModel: GameplayViewModel = sharedViewModel(navController, "gameplay_root")
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(gameType, difficultyLevel) {
        viewModel.setGameConfiguration(gameType, difficultyLevel)
    }

    BackHandler {
        viewModel.onAction(GameplayAction.OnBackClicked)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is GameplayEvent.NavigateBackToHome -> {
                navController.popBackStack(route = HomeScreen, inclusive = false)
            }
            is GameplayEvent.NavigateToResult -> {
                navController.navigateToResult()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onAction(GameplayAction.ShowPreview)
    }

    GameplayScreen(
        onBackClick = { viewModel.onAction(GameplayAction.OnBackClicked) },
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun GameplayScreen(
    onBackClick: () -> Unit,
    state: GameplayState,
    onAction: (GameplayAction) -> Unit,
) {
    val canDrawing = !state.isPreviewVisible

    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                actions = {
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
                .padding(start = 16.dp, end = 16.dp, top = 53.dp, bottom = 24.dp)
        ) {
            if (state.isPreviewVisible) {
                val drawing = state.previewDrawing

                ScribbleDashScreenTitle(
                    headline = {
                        Text(
                            text = stringResource(R.string.ready_set),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(32.dp))
                ScribbleDashDrawingArea(
                    currentPath = null,
                    paths = emptyList(),
                    exampleDrawing = drawing,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    canDrawing = canDrawing
                )
                Spacer(modifier = Modifier.height(180.dp))
                CountdownText(state.remainingTime)
            } else {
                ScribbleDashScreenTitle(
                    headline = {
                        Text(
                            text = stringResource(R.string.time_to_draw),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(32.dp))
                ScribbleDashDrawingArea(
                    currentPath = state.currentPath,
                    paths = state.paths,
                    exampleDrawing = null,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    canDrawing = canDrawing
                )
                Spacer(modifier = Modifier.height(192.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ScribbleDashIconButton(
                        icon = R.drawable.ic_reply,
                        onClick = { onAction(GameplayAction.OnUndoClick) },
                        modifier = Modifier
                            .size(64.dp),
                        isActive = state.isUndoButtonActive
                    )
                    ScribbleDashIconButton(
                        icon = R.drawable.ic_forward,
                        onClick = { onAction(GameplayAction.OnRedoClick) },
                        modifier = Modifier
                            .size(64.dp),
                        isActive = state.isRedoButtonActive
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ScribbleDashButton(
                        description = stringResource(R.string.done),
                        onClick = { onAction(GameplayAction.OnDoneClick) },
                        buttonColor = if (state.isClearCanvasButtonActive) Color(0xFF0DD280) else Color(0xFFE1D5CA),
                        modifier = Modifier
                            .width(124.dp)
                            .height(64.dp),
                        isActive = state.isClearCanvasButtonActive
                    )
                }
            }
        }
    }
}

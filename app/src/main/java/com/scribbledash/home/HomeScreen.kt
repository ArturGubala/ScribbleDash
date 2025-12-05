package com.scribbledash.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashGameModeCard
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.screens.difficultylevel.navigation.navigateToDifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.GradientScheme
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashDrawingCounter
import com.scribbledash.gameplay.navigation.GAMEPLAY_GRAPH_ROUTE
import com.scribbledash.gameplay.presentation.GameplayViewModel
import com.scribbledash.gameplay.utils.sharedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvents.NavigateToDifficultyLevel -> {
                navController.navigateToDifficultyLevel(event.gameType)
            }
        }
    }

    HomeScreen(
        onAction = viewModel::onAction,
        state = state
    )
}

@Composable
private fun HomeScreen(
    onAction: (HomeAction) -> Unit,
    state: HomeState
) {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                leadingContent = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                trailingContent = {
                    ScribbleDashDrawingCounter(
                        value = state.coins.toString(),
                        modifier = Modifier
                            .width(76.dp),
                        icon = R.drawable.ic_coin
                    )
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(GradientScheme.PrimaryGradient)
            .systemBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(83.dp))
            ScribbleDashScreenTitle(
                headline = {
                    Text(
                        text = stringResource(R.string.start_drawing),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                subtitle = {
                    Text(
                        text = stringResource(R.string.select_game_mode),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScribbleDashGameModeCard(
                image = R.drawable.ic_one_round_wonder,
                description = R.string.one_round_wonder,
                borderColor = Color(0xFF0DD280),
                onClick = {
                    onAction(HomeAction.OnGameModeTypeClick(GameType.ONE_ROUND_WONDER))
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ScribbleDashGameModeCard(
                image = R.drawable.ic_speed_draw,
                description = R.string.speed_draw,
                borderColor = Color(0xFF238CFF),
                onClick = {
                    onAction(HomeAction.OnGameModeTypeClick(GameType.SPEED_DRAW))
                },
                imageVerticalAlignment = Alignment.Top
            )
            Spacer(modifier = Modifier.height(12.dp))
            ScribbleDashGameModeCard(
                image = R.drawable.ic_endless_mode,
                description = R.string.endless_mode,
                borderColor = Color(0xFFFA852C),
                onClick = {
                    onAction(HomeAction.OnGameModeTypeClick(GameType.ENDLESS))
                }
            )
        }
    }
}

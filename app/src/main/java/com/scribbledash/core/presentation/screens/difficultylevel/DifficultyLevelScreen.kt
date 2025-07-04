package com.scribbledash.core.presentation.screens.difficultylevel

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashDifficultyLevelButton
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.DifficultyLevel
import com.scribbledash.core.presentation.utils.GameType
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.navigation.navigateToGameplay
import com.scribbledash.home.navigation.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun DifficultyLevelRoute(
    navController: NavController,
    gameType: GameType,
    viewModel: DifficultyLevelViewModel = koinViewModel()
) {
    BackHandler {
        viewModel.onAction(DifficultyLevelAction.OnBackClicked)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is DifficultyLevelEvent.NavigateToGameplay -> {
                navController.navigateToGameplay(
                    gameType = gameType,
                    difficultyLevel = event.difficultyLevel
                )
            }
            is DifficultyLevelEvent.NavigateBackToHome -> {
                navController.popBackStack(route = HomeScreen, inclusive = false)
            }
        }
    }

    DifficultyLevelScreen(
        onAction = viewModel::onAction
    )
}

@Composable
fun DifficultyLevelScreen(
    onAction: (DifficultyLevelAction) -> Unit
) {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                actions = {
                    IconButton(
                        onClick = { onAction(DifficultyLevelAction.OnBackClicked) }
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(126.dp))
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
                        text = stringResource(R.string.choose_difficulty_setting),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )

            Spacer(modifier = Modifier.height(55.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScribbleDashDifficultyLevelButton(
                    image = {
                        Image(
                            painter = painterResource(R.drawable.ic_beginner),
                            contentDescription = null,
                        )
                    },
                    description = R.string.difficulty_beginner,
                    onClick = {
                        onAction(
                            DifficultyLevelAction.OnDifficultyLevelClick(DifficultyLevel.BEGINNER)
                        )
                    },
                    imageAlignment = Alignment.TopEnd
                )
                ScribbleDashDifficultyLevelButton(
                    image = {
                        Image(
                            painter = painterResource(R.drawable.ic_challenging),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    description = R.string.difficulty_challenging,
                    onClick = {
                        onAction(
                            DifficultyLevelAction.OnDifficultyLevelClick(DifficultyLevel.CHALLENGING)
                        )
                    },
                    modifier = Modifier.offset(y = (-16).dp)
                )
                ScribbleDashDifficultyLevelButton(
                    image = {
                        Image(
                            painter = painterResource(R.drawable.ic_master),
                            contentDescription = null,
                        )
                    },
                    description = R.string.difficulty_master,
                    onClick = {
                        onAction(
                            DifficultyLevelAction.OnDifficultyLevelClick(DifficultyLevel.MASTER)
                        )
                    }
                )
            }
        }
    }
}

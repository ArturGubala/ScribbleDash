package com.scribbledash.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.domain.model.GameModeType
import com.scribbledash.core.presentation.components.ScribbleDashGameModeButton
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.screens.difficultylevel.navigation.navigateToDifficultyLevel
import com.scribbledash.core.presentation.utils.GradientScheme
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvents.NavigateToDifficultyLevel -> {
                navController.navigateToDifficultyLevel(event.finalDestination)
            }
        }
    }

    HomeScreen(
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    onAction: (HomeAction) -> Unit
) {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
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
            ScribbleDashGameModeButton(
                image = R.drawable.ic_one_round_wonder,
                description = R.string.one_round_wonder,
                onClick = {
                    onAction(HomeAction.OnGameModeTypeClick(GameModeType.OneRoundWonder.mode))
                }
            )
        }
    }
}

package com.scribbledash.gameplay.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashButton
import com.scribbledash.gameplay.components.ScribbleDashDrawingArea
import com.scribbledash.gameplay.model.ScribbleDashPath
import com.scribbledash.gameplay.model.computeBounds
import com.scribbledash.gameplay.model.toAndroidPath
import com.scribbledash.gameplay.presentation.GameplayAction
import com.scribbledash.gameplay.presentation.GameplayEvent
import com.scribbledash.gameplay.presentation.GameplayState
import com.scribbledash.gameplay.presentation.GameplayViewModel
import com.scribbledash.gameplay.utils.sharedViewModel
import com.scribbledash.home.navigation.HomeScreen

val oopsFeedbackList = listOf(
    R.string.feedback_oops_1,
    R.string.feedback_oops_2,
    R.string.feedback_oops_3,
    R.string.feedback_oops_4,
    R.string.feedback_oops_5,
    R.string.feedback_oops_6,
    R.string.feedback_oops_7,
    R.string.feedback_oops_8,
    R.string.feedback_oops_9,
    R.string.feedback_oops_10,
)

val goodFeedbackList = listOf(
    R.string.feedback_good_1,
    R.string.feedback_good_2,
    R.string.feedback_good_3,
    R.string.feedback_good_4,
    R.string.feedback_good_5,
    R.string.feedback_good_6,
    R.string.feedback_good_7,
    R.string.feedback_good_8,
    R.string.feedback_good_9,
    R.string.feedback_good_10,
)

val woohooFeedbackList = listOf(
    R.string.feedback_woohoo_1,
    R.string.feedback_woohoo_2,
    R.string.feedback_woohoo_3,
    R.string.feedback_woohoo_4,
    R.string.feedback_woohoo_5,
    R.string.feedback_woohoo_6,
    R.string.feedback_woohoo_7,
    R.string.feedback_woohoo_8,
    R.string.feedback_woohoo_9,
    R.string.feedback_woohoo_10,
)

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
internal fun ResultRoute(
    navController: NavController
) {
    val viewModel: GameplayViewModel = sharedViewModel(navController, "gameplay_root")
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
                .padding(start = 16.dp, end = 16.dp, top = 84.dp, bottom = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val randomFeedbackId = remember(state.score) {
                getRandomFeedback(state.score.toInt())
            }

            val scoreHeadline: String = when (state.score.toInt()) {
                in (0..39) -> "Oops"
                in (40..69) -> "Meh"
                in (70..79) -> "Good"
                in (80..89) -> "Great"
                else -> "Woohoo!"
            }

            ScribbleDashScreenTitle(
                headline = {
                    Text(
                        text = "${"%.0f".format(state.score)}%",
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 47.dp),
            )

            // Canvasy
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
                description = stringResource(R.string.try_again),
                onClick = { onAction(GameplayAction.OnTryAgainClick(gameType = state.gameType)) },
                buttonColor = Color(0xFF238CFF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                isActive = true
            )
        }
    }
}

fun getRandomFeedback(score: Int): Int {
    val feedbackList = when {
        score < 70 -> oopsFeedbackList
        score < 90 -> goodFeedbackList
        else -> woohooFeedbackList
    }
    return feedbackList.random()
}

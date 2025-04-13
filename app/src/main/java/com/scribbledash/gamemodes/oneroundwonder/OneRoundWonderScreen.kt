package com.scribbledash.gamemodes.oneroundwonder

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashButton
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashDrawingArea
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashIconButton
import com.scribbledash.home.navigation.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OneRoundWonderRoute(
    navController: NavController,
    viewModel: OneRoundWonderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.onAction(OneRoundWonderAction.OnBackClicked)
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is OneRoundWonderEvent.NavigateBackToHome -> {
                navController.popBackStack(route = HomeScreen, inclusive = false)
            }
        }
    }

    OneRoundWonderScreen(
        onBackClick = { viewModel.onAction(OneRoundWonderAction.OnBackClicked) },
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun OneRoundWonderScreen(
    onBackClick: () -> Unit,
    state: OneRoundWonderState,
    onAction: (OneRoundWonderAction) -> Unit
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
                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(53.dp))
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
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(36.dp),
                        ambientColor = Color(0x1F726558),
                        spotColor = Color(0x1F726558)
                    )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScribbleDashIconButton(
                    icon = R.drawable.ic_reply,
                    onClick = { onAction(OneRoundWonderAction.OnUndoClick) },
                    modifier = Modifier
                        .size(64.dp),
                    isActive = state.isUndoButtonActive
                )
                ScribbleDashIconButton(
                    icon = R.drawable.ic_forward,
                    onClick = { onAction(OneRoundWonderAction.OnRedoClick) },
                    modifier = Modifier
                        .size(64.dp),
                    isActive = state.isRedoButtonActive
                )
                ScribbleDashButton(
                    description = stringResource(R.string.clear_canvas),
                    onClick = { onAction(OneRoundWonderAction.OnClearCanvasClick) },
                    modifier = Modifier
                        .width(201.dp)
                        .height(64.dp),
                    isActive = state.isClearCanvasButtonActive
                )
            }
        }
    }
}

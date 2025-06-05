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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.domain.model.Drawings
import com.scribbledash.core.presentation.components.CountdownText
import com.scribbledash.core.presentation.components.ScribbleDashScreenTitle
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.core.presentation.utils.getDrawableRawIdForDrawing
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashButton
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashDrawingArea
import com.scribbledash.gamemodes.oneroundwonder.components.ScribbleDashIconButton
import com.scribbledash.gamemodes.oneroundwonder.model.PathData
import com.scribbledash.gamemodes.oneroundwonder.utils.VectorXmlParser
import com.scribbledash.home.navigation.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OneRoundWonderRoute(
    navController: NavController,
    viewModel: OneRoundWonderViewModel = koinViewModel(),
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

    LaunchedEffect(Unit) {
        viewModel.onAction(OneRoundWonderAction.ShowPreview)
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
    onAction: (OneRoundWonderAction) -> Unit,
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
        // TODO: move loading drawings to more proper place
        var allPathData = mutableListOf<PathData>()
        var viewport: VectorXmlParser.ViewportSize? = null

//        listOf(Drawings.entries.first { it == Drawings.HOTAIRBALLOON }).forEach {
        listOf(Drawings.entries.random()).forEach {
            println(it.name)
            val drawable = getDrawableRawIdForDrawing(it)

            val pathData = VectorXmlParser.loadPathsFromRawXml(LocalContext.current, drawable)
            val drawingViewport = VectorXmlParser.getViewportSize(context = LocalContext.current, xmlResourceId = drawable)
            viewport = VectorXmlParser.ViewportSize(
                drawingViewport?.height ?: 0f,
                drawingViewport?.width ?: 0f
            )
            pathData.let { allPathData.addAll(pathData) }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, top = 53.dp, bottom = 24.dp)
        ) {
            if (state.isPreviewVisible) {
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
                    paths = allPathData,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    canDrawing = canDrawing,
                    width = viewport!!.width,
                    height = viewport!!.height
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
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    canDrawing = canDrawing,
                    width = viewport!!.width,
                    height = viewport!!.height
                )
                Spacer(modifier = Modifier.height(192.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                    Spacer(modifier = Modifier.weight(1f))
                    ScribbleDashButton(
                        description = stringResource(R.string.done),
                        onClick = { onAction(OneRoundWonderAction.OnClearCanvasClick) },
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

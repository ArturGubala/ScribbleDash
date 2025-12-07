package com.scribbledash.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.ObserveAsEvents
import com.scribbledash.gameplay.components.ScribbleDashIconPill
import com.scribbledash.ui.theme.ScribbleDashTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ShopRoute(
    navController: NavController,
    viewModel: ShopViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
        }
    }

    ShopScreen(
        onAction = viewModel::onAction,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ShopScreen(
    onAction: (ShopAction) -> Unit,
    state: ShopState,
) {
    val pagerState = rememberPagerState(pageCount = { Destination.entries.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                leadingContent = {
                    Text(
                        text = stringResource(R.string.shop),
                        style = MaterialTheme.typography.labelLarge,
                    )
                },
                trailingContent = {
                    ScribbleDashIconPill(
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
            .background(Color(0xFFFEFAF6))
            .systemBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            ShopTabRow(
                selectedDestination = pagerState.currentPage,
                onDestinationSelected = { index, _ ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (Destination.entries[page]) {
                    Destination.SONGS -> SongsScreen()
                    Destination.ALBUM -> AlbumScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShopTabRow(
    selectedDestination: Int,
    onDestinationSelected: (Int, Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    SecondaryTabRow(
        selectedTabIndex = selectedDestination,
        containerColor = Color.Transparent,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                color = Color.Transparent
            )
        },
        divider = { },
        modifier = modifier
    ) {
        Destination.entries.forEachIndexed { index, destination ->
            ShopTab(
                destination = destination,
                selected = selectedDestination == index,
                onClick = { onDestinationSelected(index, destination) },
                isFirst = index == 0
            )
        }
    }
}

@Composable
private fun ShopTab(
    destination: Destination,
    selected: Boolean,
    onClick: () -> Unit,
    isFirst: Boolean,
    modifier: Modifier = Modifier
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            .padding(
                start = if (isFirst) 0.dp else 4.dp,
                end = if (isFirst) 4.dp else 0.dp
            )
            .background(
                color = if (selected) Color(0xFFEEE7E0) else Color(0x80EEE7E0),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .then(
                if (!selected) {
                    Modifier.drawBehind {
                        val strokeWidth = 4.dp.toPx()
                        drawLine(
                            color = Color(0xFFFEFAF6),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = strokeWidth
                        )
                    }
                } else {
                    Modifier
                }
            ),
        text = {
            Text(
                text = destination.label,
                maxLines = 2
            )
        }
    )
}

@Preview
@Composable
private fun ShopScreenPreview() {
    ScribbleDashTheme {
        ShopScreen(
            onAction = {},
            state = ShopState()
        )
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val contentDescription: String,
) {
    SONGS("songs", "Songs", "Songs"),
    ALBUM("album", "Album", "Album")
}

@Composable
fun SongsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(
            color = Color(0xFFEEE7E0),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text("Songs Screen")
    }
}

@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(
            color = Color(0xFFEEE7E0),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text("Album Screen")
    }
}

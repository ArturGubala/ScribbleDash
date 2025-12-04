package com.scribbledash.statistics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.core.presentation.utils.StatisticsType
import com.scribbledash.statistics.components.StatisticCardIcon
import com.scribbledash.statistics.components.StatisticsCard
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun StatisticRoute(
    onBackClick: () -> Unit,
    viewModel: StatisticsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    StatisticScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
private fun StatisticScreen(
    state: StatisticsState,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                leadingContent = {
                    Text(
                        text = stringResource(R.string.statistic_top_app_bar_title),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(Color(0xFFFEFAF6))
            .systemBarsPadding()
    ) { padding ->
        if (state.statistics.all { it.value == 0f }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_palette),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = stringResource(R.string.statistics_dummy_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF7F7163)
                )
                Text(
                    text = stringResource(R.string.statistics_dummy_description),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(start = 16.dp, end = 16.dp, top = 19.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // TODO change getIdentifier for something better, this is first AI solution
                items(state.statistics) { statistic ->
                    val context = LocalContext.current
                    val iconResId = context.resources.getIdentifier(
                        statistic.iconResName,
                        "drawable",
                        context.packageName
                    )
                    val descriptionResId = context.resources.getIdentifier(
                        statistic.descriptionResName,
                        "string",
                        context.packageName
                    )

                    val formattedValue = when (statistic.type) {
                        StatisticsType.ACCURACY -> "${statistic.value.toInt()}%"
                        StatisticsType.COUNT -> "${statistic.value.toInt()}"
                    }

                    val cardBackgroundColor = when (statistic.displayOrder.toInt()) {
                        0 -> Color(0x1A742EFC)
                        1 -> Color(0x1A01D2FC)
                        2 -> Color(0x1AFFB418)
                        else -> Color(0x1AFFBEB4)
                    }

                    StatisticsCard(
                        leadingIcon = {
                            StatisticCardIcon(
                                icon = if (iconResId != 0) iconResId else R.drawable.ic_palette_outlined,
                                modifier = Modifier.background(
                                    color = cardBackgroundColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            )
                        },
                        trackedDescription = if (descriptionResId != 0) stringResource(id = descriptionResId) else "",
                        trackedValue = formattedValue
                    )
                }
            }
        }
    }
}

package com.scribbledash.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.core.presentation.components.ScribbleDashTopAppBar
import com.scribbledash.statistic.components.StatisticCard
import com.scribbledash.statistic.components.StatisticCardIcon

@Composable
internal fun StatisticRoute(
    onBackClick: () -> Unit
) {
    StatisticScreen()
}

@Composable
private fun StatisticScreen() {
    Scaffold(
        topBar = {
            ScribbleDashTopAppBar(
                centerContent = {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, top = 19.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(19.dp))
            StatisticCard(
                leadingIcon = {
                    StatisticCardIcon(
                        icon = R.drawable.ic_hourglass,
                        modifier = Modifier.background(
                            color = Color(0x1A742EFC),
                            shape = RoundedCornerShape(12.dp)
                        )
                    )
                },
                trackedDescription = "Nothing to track...for now",
                trackedValue = "87%"
            )
            StatisticCard(
                leadingIcon = {
                    StatisticCardIcon(
                        icon = R.drawable.ic_bolt,
                        modifier = Modifier.background(
                            color = Color(0x1A01D2FC),
                            shape = RoundedCornerShape(12.dp)
                        )
                    )
                },
                trackedDescription = "Nothing to track...for now",
                trackedValue = "156"
            )
        }
    }
}

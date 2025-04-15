package com.scribbledash.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
                title = {
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
                .padding(horizontal = 16.dp)
        ) {

        }
    }
}


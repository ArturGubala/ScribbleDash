package com.scribbledash.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.utils.GradientScheme

@Composable
internal fun HomeRoute(
    onBackClick: () -> Unit
) {
    HomeScreen(
        onBackClick = onBackClick
    )
}

@Composable
private fun HomeScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(GradientScheme.PrimaryGradient)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Home screen")
        }
    }
}

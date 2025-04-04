package com.scribbledash.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scribbledash.R
import com.scribbledash.ui.theme.ScribbleDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScribbleDashTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit) = {},
    actions: @Composable (RowScope.() -> Unit) = {}

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(72.dp)
            .padding(horizontal = 16.dp, vertical = 21.dp)
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            title()
            actions()
        }
    }
}

@Preview
@Composable
fun ScribbleDashTopAppBarPreview() {
    ScribbleDashTheme {
        ScribbleDashTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
    }
}

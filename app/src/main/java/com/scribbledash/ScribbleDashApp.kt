package com.scribbledash

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.navigation.ScribbleDashNavHost
import com.scribbledash.core.presentation.navigation.ScribbleDashNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ScribbleDashCore: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ScribbleDashCore)
            modules()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScribbleDashApp(
    appState: ScribbleDashAppState
) {
    Scaffold(
        bottomBar = {
            ScribbleDashNavigation(navController = appState.navController)
        },
        containerColor = Color.Transparent
    ) {
        ScribbleDashNavHost(
            appState = appState,
        )
    }
}

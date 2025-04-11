package com.scribbledash

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import com.scribbledash.core.presentation.navigation.ScribbleDashNavHost
import com.scribbledash.core.presentation.navigation.ScribbleDashNavigation
import com.scribbledash.core.presentation.navigation.isTopLevelDestinationInHierarchy
import com.scribbledash.core.presentation.screens.difficultylevel.di.difficultyLevelViewModel
import com.scribbledash.gamemodes.oneroundwonder.di.oneRoundWonderViewModelModule
import com.scribbledash.home.di.homeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ScribbleDashCore: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ScribbleDashCore)
            modules(
                homeViewModelModule,
                oneRoundWonderViewModelModule,
                difficultyLevelViewModel
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScribbleDashApp(
    appState: ScribbleDashAppState
) {
    var showBottomAppBar = appState.topLevelDestinations.any {
        appState.currentDestination.isTopLevelDestinationInHierarchy(it)
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomAppBar,
                enter = fadeIn(animationSpec = tween(700)) + slideInVertically(animationSpec = tween(700)) { it },
                exit = fadeOut(animationSpec = tween(700)) + slideOutVertically(animationSpec = tween(700)) { it }
            ) {
                ScribbleDashNavigation(appState = appState)
            }
        },
        containerColor = Color.Transparent
    ) {
        ScribbleDashNavHost(
            appState = appState,
        )
    }
}

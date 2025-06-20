package com.scribbledash.gameplay.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> sharedViewModel(
    navController: NavController,
    route: String
): VM {
    val parentEntry = remember(navController) {
        navController.getBackStackEntry(route)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

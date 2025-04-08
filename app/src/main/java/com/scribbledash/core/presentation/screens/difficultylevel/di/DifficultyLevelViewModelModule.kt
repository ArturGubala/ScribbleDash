package com.scribbledash.core.presentation.screens.difficultylevel.di

import com.scribbledash.core.presentation.screens.difficultylevel.DifficultyLevelViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val difficultyLevelViewModel = module {
    viewModelOf(::DifficultyLevelViewModel)
}

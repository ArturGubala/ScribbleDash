package com.scribbledash.gamemodes.oneroundwonder.di

import com.scribbledash.gamemodes.oneroundwonder.OneRoundWonderViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val oneRoundWonderViewModelModule = module {
    viewModelOf(::OneRoundWonderViewModel)
}

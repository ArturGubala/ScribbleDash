package com.scribbledash.gamemodes.oneroundwonder.di

import com.scribbledash.gamemodes.oneroundwonder.OneRoundWonderViewModel
import com.scribbledash.gamemodes.oneroundwonder.utils.VectorXmlParser
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val oneRoundWonderViewModelModule = module {
    viewModelOf(::OneRoundWonderViewModel)
    single { VectorXmlParser(get()) }
}

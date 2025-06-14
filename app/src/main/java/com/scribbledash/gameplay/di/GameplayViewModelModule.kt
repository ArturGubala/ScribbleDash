package com.scribbledash.gameplay.di

import com.scribbledash.gameplay.GameplayViewModel
import com.scribbledash.gameplay.utils.VectorXmlParser
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val gameplayViewModelModule = module {
    viewModelOf(::GameplayViewModel)
    single { VectorXmlParser(get()) }
}

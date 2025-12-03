package com.scribbledash.statistics.di

import com.scribbledash.statistics.StatisticsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val statisticsViewModelModule = module {
    viewModelOf(::StatisticsViewModel)
}

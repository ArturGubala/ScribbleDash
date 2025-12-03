package com.scribbledash.di

import com.scribbledash.data.local.ScribbleDashDatabase
import com.scribbledash.data.repository.StatisticsRepositoryImpl
import com.scribbledash.domain.repository.StatisticsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { ScribbleDashDatabase.getDatabase(androidContext()) }
    single { get<ScribbleDashDatabase>().statisticDao() }
    single<StatisticsRepository> { StatisticsRepositoryImpl(get()) }
}

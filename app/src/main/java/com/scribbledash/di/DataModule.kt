package com.scribbledash.di

import com.scribbledash.data.local.ScribbleDashDatabase
import com.scribbledash.data.repository.StatisticsRepositoryImpl
import com.scribbledash.data.repository.WalletRepositoryImpl
import com.scribbledash.domain.repository.StatisticsRepository
import com.scribbledash.domain.repository.WalletRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { ScribbleDashDatabase.getDatabase(androidContext()) }
    single { get<ScribbleDashDatabase>().statisticDao() }
    single { get<ScribbleDashDatabase>().walletDao() }
    singleOf(::StatisticsRepositoryImpl).bind<StatisticsRepository>()
    singleOf(::WalletRepositoryImpl).bind<WalletRepository>()
}

package com.scribbledash.shop.di

import com.scribbledash.shop.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val shopViewModelModule = module {
    viewModelOf(::ShopViewModel)
}

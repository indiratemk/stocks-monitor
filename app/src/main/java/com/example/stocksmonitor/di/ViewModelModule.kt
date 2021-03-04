package com.example.stocksmonitor.di

import com.example.stocksmonitor.data.repository.StocksRepositoryImpl
import com.example.stocksmonitor.ui.stocks.StocksViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StocksViewModel(get() as StocksRepositoryImpl) }
}
package com.example.stocksmonitor.di

import com.example.stocksmonitor.data.repository.StocksRepositoryImpl
import com.example.stocksmonitor.ui.details.StockDetailsViewModel
import com.example.stocksmonitor.ui.favourite.FavouriteStocksViewModel
import com.example.stocksmonitor.ui.search.SearchViewModel
import com.example.stocksmonitor.ui.stocks.StocksViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StocksViewModel(get() as StocksRepositoryImpl) }

    viewModel { FavouriteStocksViewModel(get() as StocksRepositoryImpl) }

    viewModel { SearchViewModel(get() as StocksRepositoryImpl) }

    viewModel { StockDetailsViewModel(get() as StocksRepositoryImpl) }
}
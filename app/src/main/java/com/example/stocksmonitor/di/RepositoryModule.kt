package com.example.stocksmonitor.di

import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.data.repository.StocksRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { StocksRepositoryImpl(get() as StocksRemoteDataSource, get() as StocksLocalDataSource) }
}
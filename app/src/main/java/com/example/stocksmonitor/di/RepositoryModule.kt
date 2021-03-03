package com.example.stocksmonitor.di

import com.example.stocksmonitor.data.repository.StocksRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { StocksRepositoryImpl(get()) }
}
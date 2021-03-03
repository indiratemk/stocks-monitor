package com.example.stocksmonitor

import android.app.Application
import com.example.stocksmonitor.di.networkModule
import com.example.stocksmonitor.di.repositoryModule
import com.example.stocksmonitor.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StocksMonitorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}
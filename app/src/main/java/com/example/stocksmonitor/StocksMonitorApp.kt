package com.example.stocksmonitor

import android.app.Application
import com.example.stocksmonitor.di.dbModule
import com.example.stocksmonitor.di.networkModule
import com.example.stocksmonitor.di.repositoryModule
import com.example.stocksmonitor.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StocksMonitorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(dbModule, networkModule, repositoryModule, viewModelModule))
        }
    }
}
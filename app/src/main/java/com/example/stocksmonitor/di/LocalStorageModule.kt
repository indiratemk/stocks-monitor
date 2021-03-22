package com.example.stocksmonitor.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.stocksmonitor.data.local.SharedPrefs
import com.example.stocksmonitor.data.local.StockDao
import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.local.StocksMonitorDatabase
import com.example.stocksmonitor.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(androidContext()) }

    single { get<StocksMonitorDatabase>().getStockDao() }

    single { provideSharedPreferences(androidApplication()) }

    single { provideStocksMonitorSharedPrefs(get()) }

    single { StocksLocalDataSource(get() as StockDao, get() as SharedPrefs) }
}

fun provideDatabase(context: Context): StocksMonitorDatabase {
    return Room.databaseBuilder(context, StocksMonitorDatabase::class.java,
        Constants.DB_NAME).build()
}

fun provideSharedPreferences(application: Application): SharedPreferences {
    return application.getSharedPreferences(Constants.STOCKS_MONITOR_PREFERENCES,
        Context.MODE_PRIVATE)
}

fun provideStocksMonitorSharedPrefs(sharedPreferences: SharedPreferences): SharedPrefs {
    return SharedPrefs(sharedPreferences)
}

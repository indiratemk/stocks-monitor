package com.example.stocksmonitor.di

import android.content.Context
import androidx.room.Room
import com.example.stocksmonitor.data.local.StockDao
import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.local.StocksMonitorDatabase
import com.example.stocksmonitor.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(androidContext()) }

    single { get<StocksMonitorDatabase>().getStockDao() }

    single { StocksLocalDataSource(get() as StockDao) }
}

fun provideDatabase(context: Context): StocksMonitorDatabase {
    return Room.databaseBuilder(context, StocksMonitorDatabase::class.java, Constants.DB_NAME).build()
}

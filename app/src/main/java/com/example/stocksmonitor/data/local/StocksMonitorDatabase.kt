package com.example.stocksmonitor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stocksmonitor.data.models.Stock

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class StocksMonitorDatabase : RoomDatabase() {

    abstract fun getStockDao(): StockDao
}
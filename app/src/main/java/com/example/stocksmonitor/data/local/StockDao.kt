package com.example.stocksmonitor.data.local

import androidx.room.*
import com.example.stocksmonitor.data.models.Stock

@Dao
interface StockDao {

    @Query("SELECT * FROM stocks")
    suspend fun getStocks(): List<Stock>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStock(stock: Stock)

    @Delete
    suspend fun deleteStock(stock: Stock)
}
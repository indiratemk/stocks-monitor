package com.example.stocksmonitor.data.local

import androidx.room.*
import com.example.stocksmonitor.data.models.Stock

@Dao
interface StockDao {

    @Query("SELECT * FROM stocks")
    suspend fun getFavouriteStocks(): List<Stock>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteStock(stock: Stock)

    @Delete
    suspend fun deleteFavouriteStock(stock: Stock)
}
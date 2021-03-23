package com.example.stocksmonitor.data.local

import com.example.stocksmonitor.data.models.Stock

class StocksLocalDataSource(
    private val stockDao: StockDao,
    private val sharedPrefs: SharedPrefs
) {

    var searchedQueries: MutableSet<String>
        get() = sharedPrefs.searchRequests
        set(value) {
            sharedPrefs.searchRequests = value
        }

    suspend fun getFavouriteStocks(): List<Stock> {
        return stockDao.getFavouriteStocks()
    }

    suspend fun addFavouriteStock(stock: Stock) {
        stockDao.insertFavouriteStock(stock)
    }

    suspend fun removeFavouriteStock(stock: Stock) {
        stockDao.deleteFavouriteStock(stock)
    }
}
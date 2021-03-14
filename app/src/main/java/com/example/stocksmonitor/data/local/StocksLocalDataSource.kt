package com.example.stocksmonitor.data.local

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

class StocksLocalDataSource(
    private val stockDao: StockDao
) {

    // TODO: 3/13/21 Can throw exception?
    suspend fun getStocks(): Resource<List<Stock>> {
        return Resource.Success(stockDao.getStocks())
    }

    suspend fun addStock(stock: Stock) {
        stockDao.insertStock(stock)
    }

    suspend fun removeStock(stock: Stock) {
        stockDao.deleteStock(stock)
    }
}
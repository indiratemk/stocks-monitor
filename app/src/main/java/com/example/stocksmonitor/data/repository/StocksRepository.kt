package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

interface StocksRepository {

    suspend fun getStocks(): Resource<List<Stock>>

    suspend fun getFavouriteStocks(): Resource<List<Stock>>

    suspend fun addFavouriteStock(stock: Stock)

    suspend fun removeFavouriteStock(stock: Stock)

    suspend fun getPopularTickers(): Resource<List<String>>

    suspend fun getStock(ticker: String): Resource<Stock>
}
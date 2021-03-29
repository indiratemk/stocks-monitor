package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.models.News
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

interface StocksRepository {

    suspend fun getStocks(tickers: String): Resource<List<Stock>>

    suspend fun getFavouriteStocks(): Resource<List<Stock>>

    suspend fun addFavouriteStock(stock: Stock)

    suspend fun removeFavouriteStock(stock: Stock)

    suspend fun getPopularTickers(): Resource<List<String>>

    suspend fun searchStocks(query: String): Resource<List<Stock>>

    suspend fun getSearchedQueries(): Resource<List<String>>

    suspend fun addSearchedQuery(query: String)

    suspend fun getNews(ticker: String): Resource<List<News>>
}
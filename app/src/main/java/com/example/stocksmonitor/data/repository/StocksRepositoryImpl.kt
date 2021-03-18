package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.utils.Resource

class StocksRepositoryImpl(
    private val stocksRemoteDataSource: StocksRemoteDataSource,
    private val stocksLocalDataSource: StocksLocalDataSource
) : StocksRepository {

    override suspend fun getStocks(): Resource<List<Stock>> {
        try {
            val stocks = stocksRemoteDataSource.getStocks()
//        val stocks = listOf<Stock>(
//                Stock("GE", "General Electric Company", 13.595f, "USD", 0.3456f, 1.153279f),
//                Stock("HON", "Honeywell International Inc.", 206.37f, "EUR", -0.6399994f, -0.3091635f),
//                Stock("MDT", "Medtronic plc", 117.6f, "RUB", -0.34999847f, -0.29673463f)
//        )
//        val stocksResource = Resource.Success(stocks)
            val favouriteStocks = stocksLocalDataSource.getStocks()
            stocks.forEach { stock ->
                if (favouriteStocks.contains(stock)) {
                    stock.isFavourite = true
                }
            }
            return Resource.Success(stocks)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: e.toString())
        }
    }

    override suspend fun getFavouriteStocks(): Resource<List<Stock>> {
        val stocks = stocksLocalDataSource.getStocks()
        return Resource.Success(stocks)
    }

    override suspend fun addFavouriteStock(stock: Stock) {
        stocksLocalDataSource.addStock(stock)
    }

    override suspend fun removeFavouriteStock(stock: Stock) {
       stocksLocalDataSource.removeStock(stock)
    }

    override suspend fun getPopularTickers(): Resource<List<String>> {
        try {
            val tickersResponse = stocksRemoteDataSource.getPopularTickers()
            if (tickersResponse.isNullOrEmpty())
                return Resource.Success(emptyList())
            val (count, tickers) = tickersResponse[0]
            if (count == null || tickers.isNullOrEmpty())
                return Resource.Success(emptyList())
            val reducedTickers = if (count > Constants.MAX_SEARCH_HINTS_COUNT)
                tickers.take(Constants.MAX_SEARCH_HINTS_COUNT)
            else
                tickers
            return Resource.Success(reducedTickers)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: e.toString())
        }
    }
}
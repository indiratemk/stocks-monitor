package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Constants.MAX_HINTS_COUNT
import com.example.stocksmonitor.utils.Resource

class StocksRepositoryImpl(
    private val stocksRemoteDataSource: StocksRemoteDataSource,
    private val stocksLocalDataSource: StocksLocalDataSource
) : StocksRepository {

    override suspend fun getStocks(): Resource<List<Stock>> {
        return Resource.fromAction {
            val stocks = stocksRemoteDataSource.getStocks()
            val favouriteStocks = stocksLocalDataSource.getStocks()
            stocks.forEach { stock ->
                if (favouriteStocks.contains(stock)) {
                    stock.isFavourite = true
                }
            }
            stocks
        }
    }

    // TODO: 3/19/21 Переписать на Resource.fromAction() ???
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
        return Resource.fromAction {
            val tickersResponse = stocksRemoteDataSource.getPopularTickers()
            if (tickersResponse.isNullOrEmpty()) {
                emptyList<String>()
            }
            val (count, tickers) = tickersResponse[0]
            when {
                count == null || tickers.isNullOrEmpty() -> emptyList()
                else -> tickers.take(if (count > MAX_HINTS_COUNT) MAX_HINTS_COUNT else count)
            }
        }
    }
}
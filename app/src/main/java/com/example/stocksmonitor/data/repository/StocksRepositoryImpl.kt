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

    private val getStocksAction: suspend (String) -> List<Stock> = { tickers ->
        val stocks = stocksRemoteDataSource.getStocks(tickers)
        val favouriteStocks = stocksLocalDataSource.getFavouriteStocks()
        stocks.forEach { stock ->
            if (favouriteStocks.contains(stock)) {
                stock.isFavourite = true
            }
        }
        stocks
    }

    override suspend fun getStocks(tickers: String): Resource<List<Stock>> {
        return Resource.fromAction {
            getStocksAction(tickers)
        }
    }

    // TODO: 3/19/21 Переписать на Resource.fromAction() ???
    override suspend fun getFavouriteStocks(): Resource<List<Stock>> {
        val stocks = stocksLocalDataSource.getFavouriteStocks()
        return Resource.Success(stocks)
    }

    override suspend fun addFavouriteStock(stock: Stock) {
        stocksLocalDataSource.addFavouriteStock(stock)
    }

    override suspend fun removeFavouriteStock(stock: Stock) {
       stocksLocalDataSource.removeFavouriteStock(stock)
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

    override suspend fun getStock(ticker: String): Resource<Stock> {
        return Resource.fromAction {
            val stocks = stocksRemoteDataSource.getStocks(ticker)
            if (stocks.isNullOrEmpty()) {
                Resource.Error("Stock not found")
            }
            val stock = stocks[0]
            val isFavourite = stocksLocalDataSource.getFavouriteStocks().contains(stock)
            stock.copy(isFavourite = isFavourite)
        }
    }

    override suspend fun searchStocks(query: String): Resource<List<Stock>> {
        return Resource.fromAction {
            val stocksShort = stocksRemoteDataSource.searchStocks(query).stocks
            if (stocksShort.isEmpty()) {
                Resource.Error("Stocks not found")
            }
            var tickers = ""
            stocksShort.forEach {
                tickers += "${it.symbol},"
            }
            tickers = tickers.substring(0, tickers.length - 1)
            getStocksAction(tickers)
        }
    }
}
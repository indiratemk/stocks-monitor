package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.models.News
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.utils.Constants.MAX_TAGS_COUNT
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

    override suspend fun getFavouriteStocks(): Resource<List<Stock>> {
        return Resource.fromAction {
            stocksLocalDataSource.getFavouriteStocks()
        }
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
                else -> tickers.take(if (count > MAX_TAGS_COUNT) MAX_TAGS_COUNT else count)
            }
        }
    }

    override suspend fun searchStocks(query: String): Resource<List<Stock>> {
        return Resource.fromAction {
            val tickers = stocksRemoteDataSource.searchTickers(query).tickers
            if (tickers.isEmpty()) {
                throw Exception("Stocks not found")
            }
            val tickersQuery = tickers.joinToString(separator = ",") { it.symbol }
            getStocksAction(tickersQuery)
        }
    }

    override suspend fun addSearchedQuery(query: String) {
        var queries = mutableSetOf<String>()
        queries.addAll(stocksLocalDataSource.searchedQueries)
        if (queries.size >= MAX_TAGS_COUNT) {
            val queriesList = queries.toMutableList()
            queriesList.removeAt(0)
            queries = queriesList.toMutableSet()
        }
        queries.add(query)
        stocksLocalDataSource.searchedQueries = queries
    }

    override suspend fun getSearchedQueries(): Resource<List<String>> {
        return Resource.fromAction {
            val queries = stocksLocalDataSource.searchedQueries
            queries.toList()
        }
    }

    override suspend fun getNews(ticker: String): Resource<List<News>> {
        return Resource.fromAction {
            val newsList = stocksRemoteDataSource.getNews(ticker).items
            if (newsList.isNullOrEmpty()) {
                listOf()
            } else {
                newsList
            }
        }
    }
}
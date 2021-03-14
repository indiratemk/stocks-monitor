package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.local.StocksLocalDataSource
import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

class StocksRepositoryImpl(
    private val stocksRemoteDataSource: StocksRemoteDataSource,
    private val stocksLocalDataSource: StocksLocalDataSource
) : StocksRepository {

    override suspend fun getStocks(): Resource<List<Stock>> {
//        val stocksResource = stocksRemoteDataSource.getStocks()
        val stocks = listOf<Stock>(
                Stock("GE", "General Electric Company", 13.595f, "USD", 0.3456f, 1.153279f),
                Stock("HON", "Honeywell International Inc.", 206.37f, "EUR", -0.6399994f, -0.3091635f),
                Stock("MDT", "Medtronic plc", 117.6f, "RUB", -0.34999847f, -0.29673463f)
        )
        val stocksResource = Resource.Success(stocks)
        val favouriteStocksResource = stocksLocalDataSource.getStocks()
        if (stocksResource.data != null && favouriteStocksResource.data != null) {
            stocksResource.data.forEach { stock ->
                if (favouriteStocksResource.data.contains(stock)) {
                    stock.isFavourite = true
                }
            }
        }
        return stocksResource
    }

    override suspend fun getFavouriteStocks(): Resource<List<Stock>> {
        return stocksLocalDataSource.getStocks()
    }

    override suspend fun addFavouriteStock(stock: Stock) {
        stocksLocalDataSource.addStock(stock)
    }

    override suspend fun removeFavouriteStock(stock: Stock) {
       stocksLocalDataSource.removeStock(stock)
    }
}
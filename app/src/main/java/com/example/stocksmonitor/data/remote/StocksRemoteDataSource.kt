package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.models.TickersResponse
import com.example.stocksmonitor.utils.Constants

class StocksRemoteDataSource(
    private val mboumApi: MboumApi
) {

    @Throws(Exception::class)
    suspend fun getStocks(): List<Stock> {
        return handleResponse { mboumApi.getStocks(Constants.TICKERS) }
    }

    @Throws(Exception::class)
    suspend fun getPopularTickers(): List<TickersResponse> {
        return handleResponse { mboumApi.getPopularTickers() }
    }

    @Throws(Exception::class)
    suspend fun getStock(ticker: String): List<Stock> {
        return handleResponse { mboumApi.getStocks(ticker) }
    }
}
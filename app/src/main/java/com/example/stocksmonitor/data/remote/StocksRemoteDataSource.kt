package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.SearchStocks
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.models.TickersResponse

class StocksRemoteDataSource(
    private val mboumApi: MboumApi,
    private val finnhubApi: FinnhubApi
) {

    @Throws(Exception::class)
    suspend fun getStocks(tickers: String): List<Stock> {
        return handleResponse { mboumApi.getStocks(tickers) }
    }

    @Throws(Exception::class)
    suspend fun getPopularTickers(): List<TickersResponse> {
        return handleResponse { mboumApi.getPopularTickers() }
    }

    @Throws(Exception::class)
    suspend fun searchStocks(query: String): SearchStocks {
        return handleResponse { finnhubApi.searchStocks(query) }
    }
}
package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.models.TickersResponse
import com.example.stocksmonitor.utils.Constants

class StocksRemoteDataSource(
    private val mboumApi: MboumApi
) {

    @Throws(Exception::class)
    suspend fun getStocks(): List<Stock> {
        try {
            val response = mboumApi.getStocks(Constants.TICKERS)
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(response.message())
        } catch (e: Exception) {
            throw Exception(e.message ?: e.toString())
        }
    }

    @Throws(Exception::class)
    suspend fun getPopularTickers(): List<TickersResponse> {
        try {
            val response = mboumApi.getPopularTickers()
            if (response.isSuccessful) {
                return response.body()!!
            }
            throw Exception(response.message())
        } catch (e: Exception) {
            throw Exception(e.message ?: e.toString())
        }
    }
}
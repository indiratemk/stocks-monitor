package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.utils.Resource
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Constants

class StocksRemoteDataSource(
    private val mboumApi: MboumApi
) {

    @Throws(Exception::class)
    suspend fun getStocks(): Resource<List<Stock>> {
        try {
            val response = mboumApi.getStocks(Constants.TICKERS)
            if (response.isSuccessful) {
                return Resource.Success(response.body()!!)
            }
            return Resource.Error(response.message())
        } catch (e: Exception) {
            return Resource.Error(e.message ?: e.toString())
        }
    }
}
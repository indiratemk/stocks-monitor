package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.models.TickersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MboumApi {

    @GET("qu/quote")
    suspend fun getStocks(@Query("symbol") tickers: String): Response<List<Stock>>

    @GET("tr/trending")
    suspend fun getPopularTickers(): Response<List<TickersResponse>>
}
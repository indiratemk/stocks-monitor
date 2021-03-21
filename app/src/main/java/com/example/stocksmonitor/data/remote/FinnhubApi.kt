package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.SearchStocks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

    @GET("search")
    suspend fun searchStocks(@Query("q") query: String): Response<SearchStocks>
}
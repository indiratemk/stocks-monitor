package com.example.stocksmonitor.data.remote

import com.example.stocksmonitor.data.models.TickersSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

    @GET("search")
    suspend fun searchTickers(@Query("q") query: String): Response<TickersSearch>
}
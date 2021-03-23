package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TickersSearch(

    @SerializedName("result")
    @Expose
    val tickers: List<Ticker>
)
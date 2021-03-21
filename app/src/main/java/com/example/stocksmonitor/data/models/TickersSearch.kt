package com.example.stocksmonitor.data.models

import com.google.gson.annotations.SerializedName

data class TickersSearch(

    @SerializedName("result")
    val tickers: List<Ticker>
)
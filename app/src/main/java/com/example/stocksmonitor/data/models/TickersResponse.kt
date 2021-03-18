package com.example.stocksmonitor.data.models

import com.google.gson.annotations.SerializedName

data class TickersResponse(

    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("quotes")
    val tickers: List<String>? = null
)
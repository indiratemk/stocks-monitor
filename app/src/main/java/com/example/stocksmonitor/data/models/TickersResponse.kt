package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TickersResponse(

    @SerializedName("count")
    @Expose
    val count: Int? = null,

    @SerializedName("quotes")
    @Expose
    val tickers: List<String>? = null
)
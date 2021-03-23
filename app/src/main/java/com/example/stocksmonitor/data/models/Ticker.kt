package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ticker(

    @SerializedName("symbol")
    @Expose
    val symbol: String
)
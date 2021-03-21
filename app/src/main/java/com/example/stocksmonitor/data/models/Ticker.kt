package com.example.stocksmonitor.data.models

import com.google.gson.annotations.SerializedName

data class Ticker(

    @SerializedName("symbol")
    val symbol: String
)
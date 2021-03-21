package com.example.stocksmonitor.data.models

import com.google.gson.annotations.SerializedName

data class StockShort(

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("description")
    val description: String? = null
)
package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stock(

    @SerializedName("symbol")
    @Expose
    val symbol: String? = null,

    @SerializedName("longName")
    @Expose
    val longName: String? = null,

    @SerializedName("regularMarketPrice")
    @Expose
    val regularPrice: Float = 0f,

    @SerializedName("currency")
    @Expose
    val currency: String? = null
)
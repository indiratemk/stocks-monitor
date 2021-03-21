package com.example.stocksmonitor.data.models

import com.google.gson.annotations.SerializedName

data class SearchStocks(

    @SerializedName("result")
    val stocks: List<StockShort>
)
package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("item")
    @Expose
    val items: List<News>? = null
)
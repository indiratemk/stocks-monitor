package com.example.stocksmonitor.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class News(

    @SerializedName("guid")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,

    @SerializedName("link")
    @Expose
    val link: String? = null
)
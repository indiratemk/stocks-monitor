package com.example.stocksmonitor.data.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stocks")
data class Stock(

    @PrimaryKey
    @NonNull
    @SerializedName("symbol")
    @Expose
    val symbol: String,

    @SerializedName("longName")
    @Expose
    val longName: String? = null,

    @SerializedName("regularMarketPrice")
    @Expose
    val regularPrice: Float? = 0f,

    @SerializedName("currency")
    @Expose
    val currency: String? = null,

    @SerializedName("regularMarketChange")
    @Expose
    val marketChange: Float? = 0f,

    @SerializedName("regularMarketChangePercent")
    @Expose
    val marketChangePercent: Float? = 0f,

    var isFavourite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return this.symbol == (other as Stock).symbol
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + (longName?.hashCode() ?: 0)
        result = 31 * result + (regularPrice?.hashCode() ?: 0)
        result = 31 * result + (currency?.hashCode() ?: 0)
        result = 31 * result + (marketChange?.hashCode() ?: 0)
        result = 31 * result + (marketChangePercent?.hashCode() ?: 0)
        result = 31 * result + isFavourite.hashCode()
        return result
    }
}
package com.example.stocksmonitor.ui.stocks

import com.example.stocksmonitor.data.models.Stock

interface StockClickListener {

    fun onFavouriteClick(stock: Stock)
}
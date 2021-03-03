package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

interface StocksRepository {

    suspend fun getStocks(): Resource<List<Stock>>
}
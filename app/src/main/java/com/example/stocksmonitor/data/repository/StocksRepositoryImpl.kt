package com.example.stocksmonitor.data.repository

import com.example.stocksmonitor.data.remote.StocksRemoteDataSource
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.utils.Resource

class StocksRepositoryImpl(
    private val stocksRemoteDataSource: StocksRemoteDataSource
) : StocksRepository {

    override suspend fun getStocks(): Resource<List<Stock>> {
        return stocksRemoteDataSource.getStocks()
    }
}
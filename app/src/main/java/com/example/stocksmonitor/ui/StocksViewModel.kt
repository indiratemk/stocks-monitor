package com.example.stocksmonitor.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class StocksViewModel(
    private val stocksRepository: StocksRepository
) : BaseViewModel() {

    private val _stocks = MutableLiveData<Resource<List<Stock>>>()

    val stocks: LiveData<Resource<List<Stock>>>
        get() = _stocks

    fun getStocks() {
        coroutineContext.launch {
            _stocks.value = Resource.Loading(true)
            _stocks.value = stocksRepository.getStocks()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}
package com.example.stocksmonitor.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.utils.Resource
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
            _stocks.value = stocksRepository.getStocks(Constants.TICKERS)
            _stocks.value = Resource.Loading(false)
        }
    }
}
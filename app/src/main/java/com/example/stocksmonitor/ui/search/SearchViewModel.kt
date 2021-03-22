package com.example.stocksmonitor.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel(
    private val stocksRepository: StocksRepository
) : BaseViewModel() {

    private val _popularTickers = MutableLiveData<Resource<List<String>>>()
    private val _searchedQueries = MutableLiveData<Resource<List<String>>>()
    private val _stocks = MutableLiveData<Resource<List<Stock>>>()

    val popularTickers: LiveData<Resource<List<String>>>
        get() = _popularTickers
    val searchedQueries: LiveData<Resource<List<String>>>
        get() = _searchedQueries
    val stocks: LiveData<Resource<List<Stock>>>
        get() = _stocks

    fun getPopularTickers() {
        coroutineContext.launch {
            _popularTickers.value = Resource.Loading(true)
            _popularTickers.value = stocksRepository.getPopularTickers()
            _popularTickers.value = Resource.Loading(false)
        }
    }

    fun getSearchedQueries() {
        coroutineContext.launch {
            _searchedQueries.value = stocksRepository.getSearchedQueries()
        }
    }

    fun addSearchedQuery(query: String) {
        coroutineContext.launch {
            stocksRepository.addSearchedQuery(query)
        }
    }

    fun searchStocks(query: String) {
        coroutineContext.launch {
            _stocks.value = Resource.Loading(true)
            _stocks.value = stocksRepository.searchStocks(query)
            _stocks.value = Resource.Loading(false)
        }
    }
}
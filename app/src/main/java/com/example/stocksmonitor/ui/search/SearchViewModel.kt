package com.example.stocksmonitor.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        scope.launch {
            _popularTickers.value = Resource.Loading(true)
            val popularTickers = withContext(Dispatchers.IO) {
                stocksRepository.getPopularTickers()
            }
            _popularTickers.value = popularTickers
            _popularTickers.value = Resource.Loading(false)
        }
    }

    fun getSearchedQueries() {
        scope.launch {
            val searchedQueries = withContext(Dispatchers.IO) {
                stocksRepository.getSearchedQueries()
            }
            _searchedQueries.value = searchedQueries
        }
    }

    fun addSearchedQuery(query: String) {
        scope.launch {
            withContext(Dispatchers.IO) {
                stocksRepository.addSearchedQuery(query)
            }
        }
    }

    fun searchStocks(query: String) {
        scope.launch {
            _stocks.value = Resource.Loading(true)
            val stocks = withContext(Dispatchers.IO) {
                stocksRepository.searchStocks(query)
            }
            _stocks.value = stocks
            _stocks.value = Resource.Loading(false)
        }
    }
}
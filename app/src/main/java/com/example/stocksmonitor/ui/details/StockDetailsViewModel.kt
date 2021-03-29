package com.example.stocksmonitor.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.News
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.launch

class StockDetailsViewModel(
    private val stocksRepository: StocksRepository
) : BaseViewModel() {

    private var _newsList = MutableLiveData<Resource<List<News>>>()

    val newsList: LiveData<Resource<List<News>>>
        get() = _newsList

    fun getNews(ticker: String) {
        coroutineContext.launch {
            _newsList.value = Resource.Loading(true)
            _newsList.value = stocksRepository.getNews(ticker)
            _newsList.value = Resource.Loading(false)
        }
    }
}
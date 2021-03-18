package com.example.stocksmonitor.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel(
    private val stocksRepository: StocksRepository
) : BaseViewModel() {

    private val _popularTickers = MutableLiveData<Resource<List<String>>>()

    val popularTickers: LiveData<Resource<List<String>>>
        get() = _popularTickers

    fun getPopularTickers() {
        coroutineContext.launch {
            _popularTickers.value = Resource.Loading(true)
            _popularTickers.value = stocksRepository.getPopularTickers()
            _popularTickers.value = Resource.Loading(false)
        }
    }
}
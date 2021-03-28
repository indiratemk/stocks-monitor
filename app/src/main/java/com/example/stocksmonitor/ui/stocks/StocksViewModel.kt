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

    companion object {
        const val PAGE_SIZE = 20
    }
    private val _stocks = MutableLiveData<Resource<List<Stock>>>()
    private val _refreshed = MutableLiveData<Boolean>()
    private var offset = 0
    private var hasMoreData = true
    private var isLoading = false
    private val totalSize = Constants.S_AND_P_TICKERS.size

    val stocks: LiveData<Resource<List<Stock>>>
        get() = _stocks
    val refreshed: LiveData<Boolean>
        get() = _refreshed

    fun getStocks(isRefreshed: Boolean) {
        val isLoadingDuplicateData = isLoading
        val isLoadedAllData = !isRefreshed && !hasMoreData
        if (isLoadingDuplicateData || isLoadedAllData) {
            return
        }
        if (isRefreshed) {
            offset = 0
        }
        isLoading = true
        coroutineContext.launch {
            _stocks.value = Resource.Loading(isLoading)
            val currentPageSize = getCurrentPageSize()
            val stocksTickers = Constants.S_AND_P_TICKERS
                .slice(offset until offset + currentPageSize)
                .joinToString(separator = ",")
            val stocks = stocksRepository.getStocks(stocksTickers)
            _refreshed.value = isRefreshed
            _stocks.value = stocks
            offset += currentPageSize
            hasMoreData = totalSize > offset
            isLoading = false
            _stocks.value = Resource.Loading(isLoading)
        }
    }

    private fun getCurrentPageSize(): Int {
        return if ((offset == 0 && totalSize > PAGE_SIZE) || totalSize / offset > 1) {
            PAGE_SIZE
        } else {
            totalSize % PAGE_SIZE
        }
    }
}
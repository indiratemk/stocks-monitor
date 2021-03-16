package com.example.stocksmonitor.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.launch

class FavouriteStocksViewModel(
    private val stocksRepository: StocksRepository
) : BaseViewModel() {

    private val _favouriteStocks = MutableLiveData<Resource<List<Stock>>>()
    private val _stock = MutableLiveData<Stock>()

    val favouriteStocks: LiveData<Resource<List<Stock>>>
        get() = _favouriteStocks
    val stock: LiveData<Stock>
        get() = _stock

    fun getFavouriteStocks() {
         coroutineContext.launch {
            _favouriteStocks.value = Resource.Loading(true)
            _favouriteStocks.value = stocksRepository.getFavouriteStocks()
            _favouriteStocks.value = Resource.Loading(false)
        }
    }

    fun updateFavouriteStock(stock: Stock) {
        coroutineContext.launch {
            if (stock.isFavourite) {
                stocksRepository.addFavouriteStock(stock)
            } else {
                stocksRepository.removeFavouriteStock(stock)
            }
            _stock.value = stock
        }
    }
}
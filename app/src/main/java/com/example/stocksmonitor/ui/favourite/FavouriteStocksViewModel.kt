package com.example.stocksmonitor.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.data.repository.StocksRepository
import com.example.stocksmonitor.ui.BaseViewModel
import com.example.stocksmonitor.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
         scope.launch {
            _favouriteStocks.value = Resource.Loading(true)
             val favouriteStocks = withContext(Dispatchers.IO) {
                 stocksRepository.getFavouriteStocks()
             }
            _favouriteStocks.value = favouriteStocks
            _favouriteStocks.value = Resource.Loading(false)
        }
    }

    fun updateFavouriteStock(stock: Stock) {
        scope.launch {
            withContext(Dispatchers.IO) {
                if (stock.isFavourite) {
                    stocksRepository.addFavouriteStock(stock)
                } else {
                    stocksRepository.removeFavouriteStock(stock)
                }
            }
            _stock.value = stock
        }
    }
}
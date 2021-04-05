package com.example.stocksmonitor.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

open class BaseViewModel : ViewModel() {

    protected val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
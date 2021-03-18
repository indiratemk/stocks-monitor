package com.example.stocksmonitor.utils

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T): Resource<T>()
    data class Loading(val isLoading: Boolean): Resource<Nothing>()
    data class Error(val message: String): Resource<Nothing>()
}
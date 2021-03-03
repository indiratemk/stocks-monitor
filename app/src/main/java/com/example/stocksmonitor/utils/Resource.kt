package com.example.stocksmonitor.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean = false
) {
    class Success<T>(data: T): Resource<T>(data = data)
    class Loading<T>(isLoading: Boolean): Resource<T>(isLoading = isLoading)
    class Error<T>(message: String): Resource<T>(message = message)
}
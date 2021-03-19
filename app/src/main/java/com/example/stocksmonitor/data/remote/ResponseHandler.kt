package com.example.stocksmonitor.data.remote

import retrofit2.Response

suspend fun <T: Any> handleResponse(action: suspend () -> Response<T>): T {
    try {
        val response = action()
        if (response.isSuccessful) {
            return response.body()!!
        }
        throw Exception(response.message())
    } catch (e: Exception) {
        e.printStackTrace()
        throw Exception(e.message ?: e.toString())
    }
}
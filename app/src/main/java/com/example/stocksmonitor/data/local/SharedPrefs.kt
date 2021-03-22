package com.example.stocksmonitor.data.local

import android.content.SharedPreferences

class SharedPrefs(
    private val sharedPrefs: SharedPreferences
) {

    companion object {
        const val SEARCH_REQUESTS = "SEARCH_REQUESTS"
    }

    var searchRequests: MutableSet<String>
        get() = sharedPrefs.getStringSet(SEARCH_REQUESTS, mutableSetOf())!!
        set(value) = sharedPrefs.edit().putStringSet(SEARCH_REQUESTS, value).apply()
}
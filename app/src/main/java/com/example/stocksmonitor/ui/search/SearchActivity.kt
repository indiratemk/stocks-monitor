package com.example.stocksmonitor.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stocksmonitor.databinding.SearchActivityBinding
import com.example.stocksmonitor.ui.search.hints.HintsAdapter

class SearchActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            return context.startActivity(intent)
        }
    }

    private lateinit var binding: SearchActivityBinding
    private val popularRequestsAdapter = HintsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            tilSearch.setStartIconOnClickListener { finish() }
        }
        initRV()
    }

    private fun initRV() {
        val hintsLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        with(binding) {
            with(rvPopularRequests) {
                layoutManager = hintsLayoutManager
                adapter = popularRequestsAdapter
                setHasFixedSize(true)
            }
        }
    }
}
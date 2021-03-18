package com.example.stocksmonitor.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stocksmonitor.databinding.SearchActivityBinding
import com.example.stocksmonitor.ui.search.hints.HintsAdapter
import com.example.stocksmonitor.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            return context.startActivity(intent)
        }
    }

    private lateinit var binding: SearchActivityBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val popularRequestsAdapter = HintsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        initUI()
        searchViewModel.getPopularTickers()
    }

    private fun subscribeObservers() {
        searchViewModel.popularTickers.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> popularRequestsAdapter.hints = resource.data
                is Resource.Error -> Toast.makeText(this, resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })
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
package com.example.stocksmonitor.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.SearchActivityBinding
import com.example.stocksmonitor.ui.favourite.FavouriteStocksViewModel
import com.example.stocksmonitor.ui.search.tags.TagClickListener
import com.example.stocksmonitor.ui.search.tags.TagsAdapter
import com.example.stocksmonitor.ui.stocks.StockClickListener
import com.example.stocksmonitor.ui.stocks.StocksAdapter
import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity :
    AppCompatActivity(),
    TagClickListener,
    StockClickListener {

    companion object {
        fun startActivityForResult(activity: Activity, requestCode: Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            return activity.startActivityForResult(intent, requestCode)
        }
    }

    private lateinit var binding: SearchActivityBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val favouriteStocksViewModel: FavouriteStocksViewModel by viewModel()
    private val popularQueriesAdapter = TagsAdapter()
    private val searchedQueriesAdapter = TagsAdapter()
    private val stocksAdapter = StocksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
        initUI()
        searchViewModel.getPopularTickers()
        searchViewModel.getSearchedQueries()
    }

    private fun subscribeObservers() {
        searchViewModel.popularTickers.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    with(binding) {
                        if (resource.isLoading) {
                            tagsPlaceholderLayout.shimmerLayout.startShimmer()
                        } else {
                            tagsPlaceholderLayout.shimmerLayout.stopShimmer()
                            tagsPlaceholderLayout.shimmerLayout.visibility = View.GONE
                            rvPopularQueries.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Success -> popularQueriesAdapter.tags = resource.data
                is Resource.Error -> Toast.makeText(this, resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })

        searchViewModel.searchedQueries.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    val searchedQueries = resource.data
                    if (searchedQueries.isNotEmpty()) {
                        searchedQueriesAdapter.tags = searchedQueries
                        binding.tvSearchedQueries.visibility = View.VISIBLE
                        binding.rvSearchedQueries.visibility = View.VISIBLE
                    }
                }
                else -> {}
            }
        })

        searchViewModel.stocks.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    with(binding) {
                        if (resource.isLoading) {
                            scvQueriesContainer.visibility = View.GONE
                            llStocks.visibility = View.GONE
                            stocksPlaceholderLayout.shimmerLayout.startShimmer()
                            stocksPlaceholderLayout.shimmerLayout.visibility = View.VISIBLE
                        } else {
                            llStocks.visibility = View.VISIBLE
                            stocksPlaceholderLayout.shimmerLayout.stopShimmer()
                            stocksPlaceholderLayout.shimmerLayout.visibility = View.GONE
                        }
                    }
                }
                is Resource.Success -> {
                    stocksAdapter.setStocks(resource.data)
                }
                is Resource.Error -> Toast.makeText(this, resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initUI() {
        with(binding) {
            tilSearch.setStartIconOnClickListener {
                if (scvQueriesContainer.isVisible) {
                    finish()
                } else {
                    scvQueriesContainer.visibility = View.VISIBLE
                    llStocks.visibility = View.VISIBLE
                    etSearch.setText("")
                }
            }
            etSearch.setOnEditorActionListener { _, actionId, event ->
                if ((event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) ||
                    actionId == EditorInfo.IME_ACTION_DONE
                ) {
                    val query = etSearch.text.toString().trim()
                    if (query.isNotEmpty()) {
                        stocksAdapter.removeStocks()
                        searchViewModel.searchStocks(query)
                        searchViewModel.addSearchedQuery(query)
                    }
                }
                true
            }
        }
        initRV()
    }

    private fun initRV() {
        popularQueriesAdapter.listener = this
        with(binding.rvPopularQueries) {
            layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL)
            adapter = popularQueriesAdapter
            setHasFixedSize(true)
        }

        searchedQueriesAdapter.listener = this
        with(binding.rvSearchedQueries) {
            layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL)
            adapter = searchedQueriesAdapter
            setHasFixedSize(true)
        }

        stocksAdapter.listener = this
        with(binding.rvStocks) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = stocksAdapter
            setHasFixedSize(true)
        }
    }

    override fun onTagClick(tag: String) {
        searchViewModel.searchStocks(tag)
        binding.etSearch.apply {
            setText(tag)
            setSelection(tag.length)
        }
    }

    override fun onFavouriteClick(stock: Stock) {
        favouriteStocksViewModel.updateFavouriteStock(stock)
        setResult(Constants.RESULT_UPDATE)
    }
}
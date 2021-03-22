package com.example.stocksmonitor.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.SearchActivityBinding
import com.example.stocksmonitor.ui.favourite.FavouriteStocksViewModel
import com.example.stocksmonitor.ui.search.hints.HintClickListener
import com.example.stocksmonitor.ui.search.hints.HintsAdapter
import com.example.stocksmonitor.ui.stocks.StockClickListener
import com.example.stocksmonitor.ui.stocks.StocksAdapter
import com.example.stocksmonitor.utils.Resource
import kotlinx.android.synthetic.main.search_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity :
    AppCompatActivity(),
    HintClickListener,
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
    private val popularRequestsAdapter = HintsAdapter()
    private val searchedQueriesAdapter = HintsAdapter()
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
                            hintsPlaceholderLayout.shimmerLayout.startShimmer()
                        } else {
                            hintsPlaceholderLayout.shimmerLayout.stopShimmer()
                            hintsPlaceholderLayout.shimmerLayout.visibility = View.GONE
                            rvPopularRequests.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Success -> popularRequestsAdapter.hints = resource.data
                is Resource.Error -> Toast.makeText(this, resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })

        searchViewModel.searchedQueries.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    val searchedQueries = resource.data
                    if (searchedQueries.isNotEmpty()) {
                        searchedQueriesAdapter.hints = searchedQueries
                        tvSearchedRequests.visibility = View.VISIBLE
                        rvSearchedRequests.visibility = View.VISIBLE
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
                            svRequestsContainer.visibility = View.GONE
                            clStocks.visibility = View.GONE
                            stocksPlaceholderLayout.shimmerLayout.startShimmer()
                            stocksPlaceholderLayout.shimmerLayout.visibility = View.VISIBLE
                        } else {
                            clStocks.visibility = View.VISIBLE
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
            tilSearch.setStartIconOnClickListener { finish() }
            etSearch.setOnEditorActionListener { _, actionId, event ->
                if ((event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    val query = etSearch.text.toString().trim()
                    searchViewModel.searchStocks(query)
                    searchViewModel.addSearchedQuery(query)
                }
                true
            }
        }
        initRV()
    }

    private fun initRV() {
        popularRequestsAdapter.listener = this
        with(binding.rvPopularRequests) {
            layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL)
            adapter = popularRequestsAdapter
            setHasFixedSize(true)
        }

        searchedQueriesAdapter.listener = this
        with(binding.rvSearchedRequests) {
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

    override fun onHintClick(hint: String) {
        searchViewModel.searchStocks(hint)
    }

    override fun onFavouriteClick(stock: Stock) {
        favouriteStocksViewModel.updateFavouriteStock(stock)
        setResult(Activity.RESULT_OK)
    }
}
package com.example.stocksmonitor.ui.details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockDetailsActivityBinding
import com.example.stocksmonitor.ui.favourite.FavouriteStocksViewModel
import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class StockDetailsActivity : AppCompatActivity() {

    companion object {
        fun startActivityForResult(
            activity: Activity,
            stock: Stock,
            requestCode: Int
        ) {
            val intent = Intent(activity, StockDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_STOCK, stock)
            return activity.startActivityForResult(intent, requestCode)
        }
    }

    private lateinit var binding: StockDetailsActivityBinding
    private val favouriteStocksViewModel: FavouriteStocksViewModel by viewModel()
    private val stockDetailsViewModel: StockDetailsViewModel by viewModel()
    private val newsAdapter = NewsAdapter()
    private lateinit var stock: Stock
    private var needUpdate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StockDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extraStock = intent.getParcelableExtra<Stock>(Constants.EXTRA_STOCK)
        if (extraStock == null) {
            finish()
        }
        this.stock = extraStock!!
        subscribeObservers()
        initUI()
        stockDetailsViewModel.getNews(stock.symbol)
    }

    private fun subscribeObservers() {
        stockDetailsViewModel.newsList.observe(this, { resource ->
            when(resource) {
                is Resource.Loading -> binding.refreshLayout.isRefreshing = resource.isLoading
                is Resource.Success -> newsAdapter.setNews(resource.data)
                is Resource.Error -> Toast.makeText(this, resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initUI() {
        initRV()
        with(binding) {
            tvTitle.text = stock.symbol
            tvSecondaryTitle.text = stock.longName
            ibBack.setOnClickListener { onBackPressed() }
            chbFavourite.isChecked = stock.isFavourite
            chbFavourite.setOnCheckedChangeListener { _, isChecked ->
                needUpdate = true
                stock.isFavourite = isChecked
                favouriteStocksViewModel.updateFavouriteStock(stock)
            }
            refreshLayout.setOnRefreshListener { stockDetailsViewModel.getNews(stock.symbol) }
        }
    }

    private fun initRV() {
        with(binding.rvNews) {
            layoutManager = LinearLayoutManager(this@StockDetailsActivity)
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        if (needUpdate) {
            val data = Intent()
            data.putExtra(Constants.EXTRA_STOCK, stock)
            setResult(Constants.RESULT_UPDATE, data)
        }
        finish()
    }
}
package com.example.stocksmonitor.ui.stocks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StocksFragmentBinding
import com.example.stocksmonitor.ui.details.StockDetailsActivity
import com.example.stocksmonitor.ui.favourite.FavouriteStocksViewModel
import com.example.stocksmonitor.utils.Constants
import com.example.stocksmonitor.utils.Resource
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class StocksFragment : Fragment(), StockClickListener {

    private var _binding: StocksFragmentBinding? = null
    private val stocksAdapter = StocksAdapter()
    private val stocksViewModel: StocksViewModel by viewModel()
    private val favouriteStocksViewModel: FavouriteStocksViewModel by sharedViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StocksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initUI()
        stocksViewModel.getStocks(true)
    }

    private fun subscribeObservers() {
        stocksViewModel.stocks.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> binding.refreshLayout.isRefreshing = resource.isLoading
                is Resource.Success -> stocksAdapter.addStocks(resource.data)
                is Resource.Error -> Toast.makeText(requireContext(), resource.message,
                        Toast.LENGTH_SHORT).show()
            }
        })

        stocksViewModel.refreshed.observe(viewLifecycleOwner, Observer { refreshed ->
            if (refreshed) {
                stocksAdapter.removeStocks()
            }
        })

        favouriteStocksViewModel.stock.observe(viewLifecycleOwner, Observer { stock ->
            stocksAdapter.updateStock(stock)
        })
    }

    private fun initUI() {
        initRV()
        binding.refreshLayout.setOnRefreshListener { stocksViewModel.getStocks(true) }
    }

    private fun initRV() {
        stocksAdapter.listener = this
        with(binding.rvStocks) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stocksAdapter
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount: Int = (layoutManager as LinearLayoutManager).childCount
                    val totalItemCount: Int = (layoutManager as LinearLayoutManager).itemCount
                    val firstVisibleItemPosition: Int = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        stocksViewModel.getStocks(false)
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_SEARCH && resultCode == Constants.RESULT_UPDATE) {
            stocksViewModel.getStocks(true)
        } else if (requestCode == Constants.REQUEST_DETAILS && resultCode == Constants.RESULT_UPDATE) {
            data?.getParcelableExtra<Stock>(Constants.EXTRA_STOCK)?.let {
                stocksAdapter.updateStock(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavouriteClick(stock: Stock) {
        favouriteStocksViewModel.updateFavouriteStock(stock)
    }

    override fun onStockClick(stock: Stock) {
        StockDetailsActivity.startActivityForResult(requireActivity(), stock, Constants.REQUEST_DETAILS)
    }
}
package com.example.stocksmonitor.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.FavouriteFragmentBinding
import com.example.stocksmonitor.ui.stocks.StockClickListener
import com.example.stocksmonitor.ui.stocks.StocksAdapter
import com.example.stocksmonitor.utils.Resource
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FavouriteFragment : Fragment(), StockClickListener {

    private var _binding: FavouriteFragmentBinding? = null
    private val stocksAdapter = StocksAdapter()
    private val favouriteStocksViewModel: FavouriteStocksViewModel by sharedViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initUI()
        favouriteStocksViewModel.getFavouriteStocks()
    }

    private fun subscribeObservers() {
        favouriteStocksViewModel.favouriteStocks.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> binding.refreshLayout.isRefreshing = resource.isLoading
                is Resource.Success -> stocksAdapter.setStocks(resource.data)
                is Resource.Error -> Toast.makeText(requireContext(), resource.message,
                        Toast.LENGTH_SHORT).show()
            }
        })

        favouriteStocksViewModel.stock.observe(viewLifecycleOwner, Observer { stock ->
            if (stock.isFavourite)
                stocksAdapter.addStock(stock)
            else
                stocksAdapter.removeStock(stock)
        })
    }

    private fun initUI() {
        initRV()
        binding.refreshLayout.setOnRefreshListener {favouriteStocksViewModel.getFavouriteStocks() }
    }

    private fun initRV() {
        stocksAdapter.listener = this
        with(binding.rvStocks) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stocksAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavouriteClick(stock: Stock) {
        favouriteStocksViewModel.updateFavouriteStock(stock)
    }
}
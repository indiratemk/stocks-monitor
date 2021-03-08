package com.example.stocksmonitor.ui.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StocksFragmentBinding
import com.example.stocksmonitor.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


class StocksFragment : Fragment() {

    private var _binding: StocksFragmentBinding? = null
    private val stocksAdapter = StocksAdapter()
    private val stocksViewModel: StocksViewModel by viewModel()

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
//        stocksViewModel.getStocks()

        val json = loadJSONFromAsset()!!
        val gson = Gson()
        val itemType = object : TypeToken<List<Stock>>() {}.type
        val stocks = gson.fromJson<List<Stock>>(json, itemType)
        stocksAdapter.stocks = stocks
    }

    // TODO: 3/4/21 REMOVE
    private fun loadJSONFromAsset(): String? {
        val json: String?
        json = try {
            val `is`: InputStream = activity!!.assets.open("stocks.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun initUI() {
        initRV()
//        binding.refreshLayout.setOnRefreshListener { stocksViewModel.getStocks() }
    }

    private fun subscribeObservers() {
        stocksViewModel.stocks.observe(viewLifecycleOwner, Observer { resource ->
            when(resource) {
                is Resource.Loading -> binding.refreshLayout.isRefreshing = resource.isLoading
                is Resource.Success -> stocksAdapter.stocks = resource.data!!
                is Resource.Error -> Toast.makeText(requireContext(), resource.message,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRV() {
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
}
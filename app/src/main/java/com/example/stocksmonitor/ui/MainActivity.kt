package com.example.stocksmonitor.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.stocksmonitor.R
import com.example.stocksmonitor.databinding.MainActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val stocksViewModel: StocksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            btnStocks.isSelected = true
            btnFavourite.isSelected = false
            btnStocks.setOnClickListener {
                btnStocks.isSelected = true
                btnFavourite.isSelected = false
                updateButtonState(btnStocks, btnStocks.isSelected)
                updateButtonState(btnFavourite, btnFavourite.isSelected)
            }
            btnFavourite.setOnClickListener {
                btnStocks.isSelected = false
                btnFavourite.isSelected = true
                updateButtonState(btnStocks, btnStocks.isSelected)
                updateButtonState(btnFavourite, btnFavourite.isSelected)
            }
        }
        stocksViewModel.getStocks()
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        if (isSelected) {
            button.setTextColor(ContextCompat.getColor(this,
                R.color.black
            ))
            button.textSize = 28f
        } else {
            button.setTextColor(ContextCompat.getColor(this,
                R.color.grey
            ))
            button.textSize = 18f
        }
    }
}
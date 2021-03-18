package com.example.stocksmonitor.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.stocksmonitor.R
import com.example.stocksmonitor.databinding.MainActivityBinding
import com.example.stocksmonitor.ui.search.SearchActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initPager()
        with(binding) {
            btnSearch.setOnClickListener { SearchActivity.startActivity(this@MainActivity) }
            btnStocks.isSelected = true
            btnFavourite.isSelected = false
            btnStocks.setOnClickListener {
                btnStocks.isSelected = true
                btnFavourite.isSelected = false
                updateButtonState(btnStocks, btnStocks.isSelected)
                updateButtonState(btnFavourite, btnFavourite.isSelected)
                viewPager.currentItem = PagerAdapter.POSITION_STOCKS
            }
            btnFavourite.setOnClickListener {
                btnStocks.isSelected = false
                btnFavourite.isSelected = true
                updateButtonState(btnStocks, btnStocks.isSelected)
                updateButtonState(btnFavourite, btnFavourite.isSelected)
                viewPager.currentItem = PagerAdapter.POSITION_FAVOURITE
            }
        }
    }

    private fun initPager() {
        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.setTabs()
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.currentItem = PagerAdapter.POSITION_STOCKS
    }

    private fun updateButtonState(button: Button, isSelected: Boolean) {
        if (isSelected) {
            button.setTextColor(ContextCompat.getColor(this,
                R.color.black
            ))
            button.textSize = 28f
        } else {
            button.setTextColor(ContextCompat.getColor(this,
                R.color.grey_200
            ))
            button.textSize = 18f
        }
    }
}
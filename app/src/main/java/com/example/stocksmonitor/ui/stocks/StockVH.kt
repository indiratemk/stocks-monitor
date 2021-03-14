package com.example.stocksmonitor.ui.stocks

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.stocksmonitor.R
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockItemBinding
import com.example.stocksmonitor.utils.Constants
import java.text.NumberFormat
import java.util.*


class StockVH(
    private val binding: StockItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(stock: Stock, listener: StockClickListener?) {
        with(binding) {
            Glide.with(itemView.context)
                .load(Constants.IMAGES_URL + stock.symbol)
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.logo_border_radius)))
                .placeholder(R.drawable.img_not_found)
                .error(R.drawable.img_not_found)
                .into(ivLogo)
            cvStock.setCardBackgroundColor(
                if (adapterPosition % 2 == 0)
                    ContextCompat.getColor(itemView.context, R.color.grey_100)
                else
                    ContextCompat.getColor(itemView.context, R.color.white)
            )
            tvTicker.text = stock.symbol
            tvCompanyName.text = stock.longName
            handlePrice(stock)
        }
    }

    private fun handlePrice(stock: Stock) {
        with(binding) {
            val currencyFormatter = when (stock.currency) {
                Constants.USD_CURRENCY -> NumberFormat.getCurrencyInstance(Locale.US).apply {
                    currency = Currency.getInstance(stock.currency)
                    maximumFractionDigits = 2
                }
                null -> NumberFormat.getCurrencyInstance().apply {
                    maximumFractionDigits = 2
                }
                else -> NumberFormat.getCurrencyInstance().apply {
                    currency = Currency.getInstance(stock.currency)
                    maximumFractionDigits = 2
                }
            }
            stock.regularPrice?.let {
                tvCurrentPrice.text = currencyFormatter.format(it)
            }
            stock.marketChange?.let { marketChange ->
                val formattedPercent = String.format("%.2f", stock.marketChangePercent)
                val formattedMarketChange = if (marketChange > 0)
                    "+" + currencyFormatter.format(stock.marketChange)
                else
                    currencyFormatter.format(marketChange)
                tvDayDelta.text = itemView.context.getString(R.string.label_day_delta,
                        formattedMarketChange, formattedPercent)
                tvDayDelta.setTextColor(
                        if (marketChange < 0)
                            ContextCompat.getColor(itemView.context, R.color.red)
                        else
                            ContextCompat.getColor(itemView.context, R.color.green)
                )
            }
        }
    }
}
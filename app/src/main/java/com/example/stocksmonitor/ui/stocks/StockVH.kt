package com.example.stocksmonitor.ui.stocks

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.stocksmonitor.R
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockItemBinding
import com.example.stocksmonitor.utils.Constants
import java.lang.IllegalArgumentException
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
            handlePrice(stock)
            cvStock.setCardBackgroundColor(
                if (adapterPosition % 2 == 0)
                    ContextCompat.getColor(itemView.context, R.color.grey_100)
                else
                    ContextCompat.getColor(itemView.context, R.color.white)
            )
            tvTicker.text = stock.symbol
            tvCompanyName.text = stock.longName
            chbFavourite.isChecked = stock.isFavourite
            chbFavourite.setOnClickListener {
                stock.isFavourite = !stock.isFavourite
                listener?.onFavouriteClick(stock)
            }
        }
    }

    private fun handlePrice(stock: Stock) {
        with(binding) {
            try {
                val currencyFormatter = getCurrencyFormatter(stock.currency)
                tvCurrentPrice.text = stock.regularPrice?.let {
                    currencyFormatter.format(it)
                }
                val marketChange = stock.marketChange
                val percentChange = stock.marketChangePercent
                val formattedMarketChange = marketChange?.let {
                    getFormattedMarketChange(currencyFormatter, it)
                }
                val formattedPercentChange = getFormattedPercent(percentChange)
                tvDayDelta.text = marketChange?.let {
                    itemView.context.getString(R.string.label_day_delta,
                        formattedMarketChange, formattedPercentChange)
                }
                tvDayDelta.setTextColor(
                    if (marketChange != null && marketChange < 0)
                        ContextCompat.getColor(itemView.context, R.color.red)
                    else
                        ContextCompat.getColor(itemView.context, R.color.green)
                )
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    private fun getCurrencyFormatter(stockCurrency: String?): NumberFormat {
        return when (stockCurrency) {
            Constants.USD_CURRENCY -> NumberFormat.getCurrencyInstance(Locale.US).apply {
                currency = Currency.getInstance(stockCurrency)
                maximumFractionDigits = 2
            }
            null -> NumberFormat.getCurrencyInstance().apply { maximumFractionDigits = 2 }
            else -> NumberFormat.getCurrencyInstance().apply {
                currency = Currency.getInstance(stockCurrency)
                maximumFractionDigits = 2
            }
        }
    }

    private fun getFormattedMarketChange(
        currencyFormatter: NumberFormat,
        marketChange: Float
    ): String {
        return if (marketChange > 0)
            itemView.context.getString(R.string.label_positive_market_change,
                currencyFormatter.format(marketChange))
        else
            currencyFormatter.format(marketChange)
    }

    private fun getFormattedPercent(percent: Float?): String {
        return String.format("%.2f", percent)
    }
}
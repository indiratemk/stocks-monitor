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

    fun bind(stock: Stock) {
        with(binding) {
            cvStock.setCardBackgroundColor(
                if (adapterPosition % 2 == 0)
                    ContextCompat.getColor(itemView.context, R.color.grey_100)
                else
                    ContextCompat.getColor(itemView.context, R.color.white)
            )
            tvTicker.text = stock.symbol
            tvCompanyName.text = stock.longName

            val currencyFormatter = when (stock.currency) {
                "USD" -> NumberFormat.getCurrencyInstance(Locale.US).apply {
                    currency = Currency.getInstance(stock.currency)
                    maximumFractionDigits = 2
                }
                else -> NumberFormat.getCurrencyInstance().apply {
                    currency = Currency.getInstance(stock.currency)
                    maximumFractionDigits = 2
                }
            }
            tvCurrentPrice.text = currencyFormatter.format(stock.regularPrice)
            tvDayDelta.setTextColor(
                if (stock.marketChange < 0 || stock.marketChangePercent < 0)
                    ContextCompat.getColor(itemView.context, R.color.red)
                else
                    ContextCompat.getColor(itemView.context, R.color.green)
            )
            val formattedPercent = String.format("%.2f", stock.marketChangePercent)
            tvDayDelta.text = itemView.context.getString(R.string.label_day_delta,
                if (stock.marketChange > 0)
                    "+${currencyFormatter.format(stock.marketChange)}"
                else
                    currencyFormatter.format(stock.marketChange), formattedPercent)


            Glide.with(itemView.context)
                .load(Constants.IMAGES_URL + stock.symbol)
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.logo_border_radius)))
                .placeholder(R.drawable.img_not_found)
                .error(R.drawable.img_not_found)
                .into(ivLogo)
        }
    }
}
package com.example.stocksmonitor.ui.stocks

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.stocksmonitor.R
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockItemBinding
import com.example.stocksmonitor.ui.Currency
import com.example.stocksmonitor.utils.Constants


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
            tvCurrentPrice.text = when (stock.currency) {
                Currency.RUB.value -> "₽${stock.regularPrice}"
                Currency.EUR.value -> "€${stock.regularPrice}"
                else -> "\$${stock.regularPrice}"
            }
            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(itemView.context)
                .load(Constants.IMAGES_URL + stock.symbol)
                .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.logo_border_radius)))
                .placeholder(R.drawable.img_not_found)
                .error(R.drawable.img_not_found)
                .into(ivLogo)
        }
    }
}
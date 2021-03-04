package com.example.stocksmonitor.ui.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockItemBinding

class StocksAdapter : RecyclerView.Adapter<StockVH>() {

    companion object {
        const val DARK_TYPE = 0
        const val WHITE_TYPE = 1
    }

    var stocks = listOf<Stock>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
        val inflater = LayoutInflater.from(parent.context)
        return StockVH(StockItemBinding.inflate(inflater, parent, false))
    }

//    override fun getItemViewType(position: Int): Int {
//        if (position % 2 == 0)
//            return DARK_TYPE
//        return WHITE_TYPE
//    }

    override fun onBindViewHolder(holder: StockVH, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount() = stocks.count()
}

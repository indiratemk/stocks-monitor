package com.example.stocksmonitor.ui.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.data.models.Stock
import com.example.stocksmonitor.databinding.StockItemBinding

class StocksAdapter : RecyclerView.Adapter<StockVH>() {

    private var stocks = ArrayList<Stock>()
    var listener: StockClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
        val inflater = LayoutInflater.from(parent.context)
        return StockVH(StockItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: StockVH, position: Int) {
        holder.bind(stocks[position], listener)
    }

    override fun getItemCount() = stocks.count()

    fun setStocks(stocks: List<Stock>) {
        this.stocks.clear()
        this.stocks.addAll(stocks)
        notifyDataSetChanged()
    }

    fun removeStock(stock: Stock) {
        val position = stocks.indexOfFirst { it.symbol == stock.symbol }
        this.stocks.removeAt(position)
        notifyDataSetChanged()
    }

    fun addStock(stock: Stock) {
        this.stocks.add(stock)
        val position = this.stocks.indexOf(stock)
        notifyItemInserted(position)
    }

    fun updateStock(stock: Stock) {
        val position = stocks.indexOfFirst { it.symbol == stock.symbol }
        this.stocks[position] = stock
        notifyItemChanged(position)
    }
}

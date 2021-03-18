package com.example.stocksmonitor.ui.search.hints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.databinding.HintItemBinding

class HintsAdapter : RecyclerView.Adapter<HintVH>() {

    var hints = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintVH {
        val inflater = LayoutInflater.from(parent.context)
        return HintVH(HintItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HintVH, position: Int) {
        holder.bind(hints[position])
    }

    override fun getItemCount() = hints.size

}
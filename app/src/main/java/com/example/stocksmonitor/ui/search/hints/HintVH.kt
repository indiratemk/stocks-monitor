package com.example.stocksmonitor.ui.search.hints

import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.databinding.HintItemBinding

class HintVH(
    private val binding: HintItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(hint: String) {
        binding.btnHint.text = hint
    }
}
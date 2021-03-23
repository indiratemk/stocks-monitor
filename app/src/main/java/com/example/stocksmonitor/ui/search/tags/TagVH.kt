package com.example.stocksmonitor.ui.search.tags

import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.databinding.TagItemBinding

class TagVH(
    private val binding: TagItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tag: String, listener: TagClickListener?) {
        with(binding.btnTag) {
            text = tag
            setOnClickListener { listener?.onTagClick(tag) }
        }
    }
}
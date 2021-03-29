package com.example.stocksmonitor.ui.details

import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.data.models.News
import com.example.stocksmonitor.databinding.NewsItemBinding

class NewsVH(
    private val binding: NewsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        with(binding) {
            tvTitle.text = news.title
            tvDescription.text = news.description
            tvLink.text = news.link
        }
    }
}
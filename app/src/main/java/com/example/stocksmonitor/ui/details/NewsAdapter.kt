package com.example.stocksmonitor.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.data.models.News
import com.example.stocksmonitor.databinding.NewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsVH>() {

    private var newsList = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val inflater = LayoutInflater.from(parent.context)
        return NewsVH(NewsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size

    fun setNews(newsList: List<News>) {
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }
}
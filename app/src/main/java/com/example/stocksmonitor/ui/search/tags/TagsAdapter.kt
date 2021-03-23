package com.example.stocksmonitor.ui.search.tags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksmonitor.databinding.TagItemBinding

class TagsAdapter : RecyclerView.Adapter<TagVH>() {

    var listener: TagClickListener? = null
    var tags = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagVH {
        val inflater = LayoutInflater.from(parent.context)
        return TagVH(TagItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: TagVH, position: Int) {
        holder.bind(tags[position], listener)
    }

    override fun getItemCount() = tags.size
}
package com.example.moviedb.ui.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.ListMovieBinding

class TopRatedAdapter(val onItemClick: (topRatedItem: ResultsItem) -> Unit) : RecyclerView.Adapter<TopRatedAdapter.TopRatedHolder>() {

    private var topRatedList = mutableListOf<ResultsItem>()

    fun addItems(topRatedList : List<ResultsItem>) {
        this.topRatedList.clear()
        this.topRatedList.addAll(topRatedList)
        notifyDataSetChanged()
    }


    class TopRatedHolder(private val binding: ListMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topRated: ResultsItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${topRated.posterPath}")
                    .into(imgMovie)
                tvName.text = topRated.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedHolder {
        val topRatedBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedHolder(topRatedBinding)
    }

    override fun onBindViewHolder(holder: TopRatedHolder, position: Int) {
        val topRatedItem = topRatedList[position]
        holder.bind(topRatedItem)
        holder.itemView.setOnClickListener { onItemClick(topRatedItem) }
    }

    override fun getItemCount(): Int = topRatedList.size
}
package com.example.moviedb.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.ListMovieBinding

class FavoriteAdapter(private val onItemClick: (item: ResultsItem) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val movieItemList = mutableListOf<ResultsItem>()

    fun addItems(movieItemList: List<ResultsItem>) {
        this.movieItemList.clear()
        this.movieItemList.addAll(movieItemList)
        notifyDataSetChanged()
    }


        class ViewHolder(private val binding: ListMovieBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(movieItem: ResultsItem) {
                with(binding) {
                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/original${movieItem.posterPath}")
                        .into(imgMovie)
                    tvName.text = movieItem.title
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val movieItemBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = movieItemList[position]
        holder.bind(movieItem)
        holder.itemView.setOnClickListener { onItemClick(movieItem) }
    }

    override fun getItemCount(): Int = movieItemList.size
}
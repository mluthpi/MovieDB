package com.example.moviedb.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.data.ResultsItems
import com.example.moviedb.databinding.ListRelatedMovieBinding

class RelatedMovieAdapter(val onItemClick: (relatedItem: ResultsItems)-> Unit) : RecyclerView.Adapter<RelatedMovieAdapter.RelatedMovieHolder>(){

    private val relatedMovieList = mutableListOf<ResultsItems>()

    fun addItems(relatedMovieList: List<ResultsItems>) {
        this.relatedMovieList.clear()
        this.relatedMovieList.addAll(relatedMovieList)
        notifyDataSetChanged()
    }

    class RelatedMovieHolder(private val binding: ListRelatedMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(relatedItem: ResultsItems) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${relatedItem.posterPath}")
                    .into(imgRelatedMovie)
                tvRelatedMovie.text = relatedItem.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedMovieHolder {
        val relatedMovieBinding = ListRelatedMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelatedMovieHolder(relatedMovieBinding)
    }

    override fun onBindViewHolder(holder: RelatedMovieHolder, position: Int) {
        val relatedMovieItem = relatedMovieList[position]
        holder.bind(relatedMovieItem)
        holder.itemView.setOnClickListener {onItemClick(relatedMovieItem)}
    }

    override fun getItemCount(): Int = relatedMovieList.size
}
package com.example.moviedb.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.data.MovieResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.FragmentNowPlayingBinding
import com.example.moviedb.databinding.ListMovieBinding

class NowPlayingAdapter(val onItemClick: (nowPlayingItem: ResultsItem)-> Unit) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingHolder>() {

    private val nowPlayingList = mutableListOf<ResultsItem>()

    fun addItems(nowPlayingList: List<ResultsItem>) {
        this.nowPlayingList.clear()
        this.nowPlayingList.addAll(nowPlayingList)
        notifyDataSetChanged()
    }

    class NowPlayingHolder(private val binding: ListMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(nowPlaying : ResultsItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${nowPlaying.posterPath}")
                    .into(imgMovie)
                tvName.text = nowPlaying.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingHolder {
        val nowPlayingBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowPlayingHolder(nowPlayingBinding)
    }

    override fun onBindViewHolder(holder: NowPlayingHolder, position: Int) {
        val nowPlayingItem = nowPlayingList[position]
        holder.bind(nowPlayingItem)
        holder.itemView.setOnClickListener { onItemClick(nowPlayingItem) }
    }

    override fun getItemCount(): Int = nowPlayingList.size
}
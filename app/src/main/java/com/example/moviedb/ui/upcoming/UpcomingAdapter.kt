package com.example.moviedb.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.FragmentUpcomingBinding
import com.example.moviedb.databinding.ListMovieBinding
import java.sql.ResultSet

class UpcomingAdapter(val onItemClick : (upComing: ResultsItem) -> Unit) : RecyclerView.Adapter<UpcomingAdapter.UpcomingHolder>() {

    private val upcomingList = mutableListOf<ResultsItem>()

    fun addItems(upcomingList: List<ResultsItem>) {
        this.upcomingList.clear()
        this.upcomingList.addAll(upcomingList)
        notifyDataSetChanged()
    }


    class UpcomingHolder(private val binding: ListMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(upcoming: ResultsItem) {
            with(binding) {
                Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/original${upcoming.posterPath}")
                    .into(imgMovie)
                tvName.text = upcoming.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingHolder {
        val upcomingBinding = ListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingHolder(upcomingBinding)
    }

    override fun onBindViewHolder(holder: UpcomingHolder, position: Int) {
        val upComingItem = upcomingList[position]
        holder.bind(upComingItem)
        holder.itemView.setOnClickListener { onItemClick(upComingItem) }
    }

    override fun getItemCount(): Int = upcomingList.size
}
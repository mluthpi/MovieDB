package com.example.moviedb.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.ViewModelFactory
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.databinding.ActivityFavoriteBinding
import com.example.moviedb.detail.DetailsActivity
import com.example.moviedb.model.MovieEntity

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var favoriteAdapter = FavoriteAdapter {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("KEY_ID", it.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        favoriteViewModel.getFavoriteMovie().observe(this,{showFavoriteMovie(it)})

    }

    private fun setupViewModel() {
        favoriteViewModel = obtainViewModel(this)
    }

    private fun showFavoriteMovie(favMovieList: List<MovieEntity>) {
        if (favMovieList.isNotEmpty()) {
            favoriteAdapter.addItems(favMovieList)
            binding.rvFavorite.visibility = View.VISIBLE
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(
                    this@FavoriteActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = favoriteAdapter
            }
        } else {
            binding.rvFavorite.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}
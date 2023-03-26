package com.example.moviedb.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.ViewModelFactory
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.data.ResultsItems
import com.example.moviedb.databinding.ActivityDetailsBinding
import com.example.moviedb.model.MovieEntity
import com.example.moviedb.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding
    private lateinit var title : String
    private lateinit var detailsViewModel: DetailsViewModel

    private val relatedMovieAdapter = RelatedMovieAdapter {
        val intent = Intent(this@DetailsActivity, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra("KEY_ID", 0)

        detailsViewModel = obtainViewModel(this)
        detailsViewModel.getDetailMovie(id)
        detailsViewModel.detailsMovie.observe(this, { movieDetails ->
            showMovieDetail(movieDetails)
        })
        detailsViewModel.getRelatedMovie(id)
        detailsViewModel.relatedMovie.observe(this, {relatedMovie ->
            showRelatedMovie(relatedMovie)
        })
        detailsViewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun showMovieDetail(movieDetailResponse : MovieDetailResponse) {
        Glide.with(this)
            .load(movieDetailResponse.posterPath)
            .into(binding.imgDetails)
        binding.titleValue.text = movieDetailResponse.title
        binding.overviewValue.text = movieDetailResponse.overview

        detailsViewModel.getFavoriteMovie().observe(this, {favMovie ->
            Log.d("TEST_DEBUG", "getFavoriteMovie running... ")
            val isFavorite = favMovie.filter { it.id == movieDetailResponse.id }.isNotEmpty()
            setUpFavoriteMovie(isFavorite,movieDetailResponse)
        })

    }

    private fun showRelatedMovie(relatedMovie : List<ResultsItems>) {
        relatedMovieAdapter.addItems(relatedMovie)
        binding.rvRelatedMovie.apply {
            layoutManager = LinearLayoutManager(
                this@DetailsActivity, RecyclerView.HORIZONTAL, false
            )
            adapter = relatedMovieAdapter
        }
    }


    private fun setUpFavoriteMovie(isFavorite: Boolean, movie: MovieDetailResponse) {
        Log.d("TEST_DEBUG", "setUpFavoriteMovie: isFavorite: $isFavorite")
        if (isFavorite) {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_24 )
            binding.fbFavorite.setOnClickListener {
                val movie = MovieEntity (
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = movie.posterPath
                        )
                detailsViewModel.deleteToDB(movie)
                Toast.makeText(this, "berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.fbFavorite.setImageResource(R.drawable.ic_favorite)

            binding.fbFavorite.setOnClickListener {
                val movie = MovieEntity (
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = movie.posterPath
                        )
                detailsViewModel.insertToDB(movie)
                Toast.makeText(this, "berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailsViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

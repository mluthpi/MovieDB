package com.example.moviedb.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.ViewModelFactory
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val id = intent.getIntExtra("KEY_ID", 0)

        detailsViewModel = obtainViewModel(this)
        detailsViewModel.getDetailMovie(id)
        detailsViewModel.detailsMovie.observe(this, {movieDetails ->
            showMovieDetail(movieDetails)
        })
        detailsViewModel.isLoading.observe(this, {isLoading ->
            showLoading(isLoading)
        })
    }



//    private fun getMovieDetails(id: Int) {
//        ApiConfig.getApiRest().getDetailsMovie(id = id, "ba7b7ec258e912a3c68b34e6dfba3ca5")
//            .enqueue(object : Callback<MovieDetailResponse> {
//                override fun onResponse(
//                    call: Call<MovieDetailResponse>,
//                    response: Response<MovieDetailResponse>
//                ) {
//                    val movie = response.body()
//                    if (movie != null) {
//                        binding.titleValue.text = movie.title
//                        binding.overviewValue.text = movie.overview
//                        binding.ratingValue.text = movie.voteAverage.toString().substring(0,3)
//                        Glide.with(this@DetailsActivity)
//                            .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
//                            .into(binding.imgDetails)
//                    }
//                }
//
//                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
//
//                }
//            })
//    }

    private fun showMovieDetail(movieDetailResponse : MovieDetailResponse) {
        Glide.with(this)
            .load(movieDetailResponse.posterPath)
            .into(binding.imgDetails)
        binding.titleValue.text = movieDetailResponse.title

        detailsViewModel.getFavoriteMovie().observe(this, {favMovie ->
            Log.d("TEST_DEBUG", "getFavoriteMovie running... ")
            val isFavorite = favMovie.filter { it.id == movieDetailResponse.id }.isNotEmpty()
            setUpFavoriteMovie(isFavorite,movieDetailResponse)
        })
    }


    private fun setUpFavoriteMovie(isFavorite: Boolean, movie: MovieDetailResponse) {
        Log.d("TEST_DEBUG", "setUpFavoriteMovie: isFavorite: $isFavorite")
        if (isFavorite) {
            binding.fbFavorite.setImageResource(R.drawable.ic_baseline_favorite_24 )
            binding.fbFavorite.setOnClickListener {
                val movie = MovieEntity (
                    id = movie.id,
                    title = movie.title,
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

}
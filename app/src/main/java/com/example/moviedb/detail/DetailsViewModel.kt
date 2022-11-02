package com.example.moviedb.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.model.MovieEntity
import com.example.moviedb.network.ApiConfig
import com.example.moviedb.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application) : ViewModel() {
    private val mMovieRepository: MovieRepository = MovieRepository(application)

    companion object {
        private const val TAG = "DetailsViewModel"
    }

    private val _detailsMovie = MutableLiveData<MovieDetailResponse>()
    val detailsMovie : LiveData<MovieDetailResponse> = _detailsMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getDetailMovie(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiRest().getDetailsMovie(id = id, "ba7b7ec258e912a3c68b34e6dfba3ca5")
        client.enqueue(object : Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailsMovie.value = response.body()
                } else {
                    Log.e(TAG, "onResponse: onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: onFailure ${t.message.toString()}")
            }
        })
    }

    fun insertToDB(movie: MovieEntity) {
        mMovieRepository.insert(movie)
    }
    fun deleteToDB(movie: MovieEntity) {
        mMovieRepository.delete(movie)
    }
    fun getFavoriteMovie() : LiveData<List<MovieEntity>> = mMovieRepository.getAllMovie()

}
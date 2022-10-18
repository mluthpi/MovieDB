package com.example.moviedb.ui.toprated

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.MovieResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.network.ApiConfig
import com.example.moviedb.ui.upcoming.UpcomingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewModel : ViewModel() {

    companion object {
        private const val TAG = "TopRated"
    }

    private val _listTopRated = MutableLiveData<List<ResultsItem>>()
    val listTopRated : LiveData<List<ResultsItem>> = _listTopRated

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getTopRated(apiKey : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiRest().getTopRated(apiKey)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listTopRated.value = (response.body()?.results?: emptyList()) as List<ResultsItem>?
                } else {
                    _listTopRated.value = emptyList()
                    Log.e(TopRatedViewModel.TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TopRatedViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
package com.example.moviedb.ui.nowplaying

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.MovieResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingViewModel : ViewModel() {

    companion object {
        private const val TAG = "NowPlaying"
    }

    private val _listNowPlaying = MutableLiveData<List<ResultsItem>>()
    val listNowPlaying: LiveData<List<ResultsItem>> = _listNowPlaying

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNowPlaying(apiKey: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiRest().getMovie(apiKey)
        client.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listNowPlaying.value = (response.body()?.results?: emptyList()) as List<ResultsItem>?
                } else {
                    _listNowPlaying.value = emptyList()
                    Log.e(TAG, "onResponse: ${response.message()}")}
                }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
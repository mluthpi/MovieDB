package com.example.moviedb.ui.upcoming

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

class UpcomingViewModel : ViewModel() {

    companion object {
        private const val TAG = "Upcoming"
    }

    private val _listUpcoming = MutableLiveData<List<ResultsItem>>()
    val listUpcoming : LiveData<List<ResultsItem>> = _listUpcoming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getUpcoming(apiKey: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiRest().getUpcoming(apiKey)
        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUpcoming.value = (response.body()?.results?: emptyList()) as List<ResultsItem>?
                } else {
                    _listUpcoming.value = emptyList()
                    Log.e(TAG, "onResponse: ${response.message()}")}
                }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}
package com.example.moviedb.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.model.MovieEntity
import com.example.moviedb.repository.MovieRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mMovieRepository = MovieRepository(application)

    fun getFavoriteMovie() : LiveData<List<MovieEntity>> = mMovieRepository.getAllMovie()
}
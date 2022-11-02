package com.example.moviedb.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.database.MovieDao
import com.example.moviedb.database.MovieRoomDatabase
import com.example.moviedb.model.MovieEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository(application: Application) {
    private val mMovieDao : MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
         val db = MovieRoomDatabase.getDatabase(application)
        mMovieDao = db.movieDao()
    }

    fun getAllMovie() : LiveData<List<MovieEntity>> = mMovieDao.getAllMovies()

    fun insert(movie: MovieEntity) {
        executorService.execute {mMovieDao.insert(movie)}
    }

    fun delete(movie: MovieEntity){
        executorService.execute {mMovieDao.delete(movie)}
    }
}
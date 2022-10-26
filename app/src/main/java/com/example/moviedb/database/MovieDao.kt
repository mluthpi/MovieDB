package com.example.moviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (movie: MovieDetailResponse)

    @Delete
    fun delete (movie: MovieDetailResponse)

    @Query("SELECT * from movieentity ORDER by title ASC")
    fun getAllMovies(): LiveData<List<ResultsItem>>


}


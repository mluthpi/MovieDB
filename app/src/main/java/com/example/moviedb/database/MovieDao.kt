package com.example.moviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.ResultsItem
import com.example.moviedb.model.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (movie: MovieEntity)

    @Delete
    fun delete (movie: MovieEntity)

    @Query("SELECT * from movieentity ORDER by title ASC")
    fun getAllMovies(): LiveData<List<MovieEntity>>


}


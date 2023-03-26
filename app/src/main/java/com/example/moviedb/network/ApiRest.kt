package com.example.moviedb.network

import android.graphics.Movie
import com.example.moviedb.data.MovieDetailResponse
import com.example.moviedb.data.MovieResponse
import com.example.moviedb.data.RelatedMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.xml.transform.Result

interface ApiRest {

    @GET("movie/now_playing")
    fun getMovie(@Query("api_key") apiKey : String) : Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcoming(@Query("api_key") apiKey: String) : Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") apiKey: String) : Call<MovieResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilar(
        @Path("movie_id") id : Int,
        @Query("api_key") apiKey: String
    ) : Call<RelatedMovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailsMovie (
        @Path("movie_id") id : Int,
        @Query("api_key") apiKey : String
    ) : Call<MovieDetailResponse>
}
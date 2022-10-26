package com.example.moviedb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("title")
    var title: String,

    @SerializedName("poster_path")
    var posterPath: String? = null
        )
package com.example.moviedb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null
        )
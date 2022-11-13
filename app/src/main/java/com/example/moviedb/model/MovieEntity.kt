package com.example.moviedb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,

    var title: String? = null,

    var overview: String? = null,

    var posterPath: String? = null
        )
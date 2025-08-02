package com.example.what3wordtesthome.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val backdropUrl: String?,
    val posterUrl: String?,
    val homepage: String?
)
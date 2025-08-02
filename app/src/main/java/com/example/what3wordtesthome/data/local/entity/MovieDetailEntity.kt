package com.example.what3wordtesthome.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val rating: Double?,
    val genres: String,
    val homepage: String?,
    val tagline: String?,
    val status: String?,
    val budget: Int?,
    val revenue: Int?
)
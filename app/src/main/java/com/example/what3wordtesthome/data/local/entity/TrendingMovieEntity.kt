package com.example.what3wordtesthome.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrendingMovie")
data class TrendingMovieEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val releaseYear: String?,
    val voteAverage: Double?,
    val imageUrl: String?
)
package com.example.what3wordtesthome.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double,
    val backdrop_path: String?,
    val poster_path: String?,
    val homepage: String?
)
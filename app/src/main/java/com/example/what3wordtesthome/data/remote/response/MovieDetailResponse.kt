package com.example.what3wordtesthome.data.remote.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class MovieDetailResponse(
    val id: Int,
    val title: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val runtime: Int?,
    val vote_average: Double?,
    val genres: List<GenreDto> = emptyList(),
    val homepage: String?,
    val tagline: String?,
    val status: String?,
    val budget: Int?,
    val revenue: Int?,
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)
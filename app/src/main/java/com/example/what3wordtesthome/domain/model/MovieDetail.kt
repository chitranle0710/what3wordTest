package com.example.what3wordtesthome.domain.model

import com.example.what3wordtesthome.data.local.entity.MovieDetailEntity
import com.example.what3wordtesthome.data.remote.response.MovieDetailResponse
import kotlin.collections.joinToString

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val rating: Double?,
    val genres: List<String>,
    val homepage: String?,
    val tagline: String?,
    val status: String?,
    val budget: Int?,
    val revenue: Int?
)

fun MovieDetailResponse.toEntity(): MovieDetailEntity = MovieDetailEntity(
    id = id,
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    posterPath = poster_path,
    backdropPath = backdrop_path,
    releaseDate = release_date,
    runtime = runtime,
    rating = vote_average,
    genres = genres.joinToString(",") { it.name },
    homepage = homepage,
    tagline = tagline,
    status = status,
    budget = budget,
    revenue = revenue
)

fun MovieDetailEntity.toDto(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    runtime = runtime,
    rating = rating,
    genres = genres.split(",").map { it.trim() },
    homepage = homepage,
    tagline = tagline,
    status = status,
    budget = budget,
    revenue = revenue
)
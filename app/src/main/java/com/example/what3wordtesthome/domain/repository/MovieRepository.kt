package com.example.what3wordtesthome.domain.repository

import com.example.what3wordtesthome.domain.model.Movie

interface MovieRepository {
    suspend fun getTrendingMovies(): List<Movie>
}
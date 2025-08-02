package com.example.what3wordtesthome.domain.usecase

import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.repository.MovieRepository

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): List<Movie> {
        return repository.searchMovies(query)
    }
}
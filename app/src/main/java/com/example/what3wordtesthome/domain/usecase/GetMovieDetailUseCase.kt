package com.example.what3wordtesthome.domain.usecase

import com.example.what3wordtesthome.domain.model.MovieDetail
import com.example.what3wordtesthome.domain.repository.MovieRepository

class GetMovieDetailUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(id: String): MovieDetail? =
        repository.getMovieDetail(id)
}
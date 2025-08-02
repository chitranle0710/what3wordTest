package com.example.what3wordtesthome.domain.usecase

import androidx.paging.PagingData
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMovieOfflinePagingUseCase (private val repository: MovieRepository) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getTrendingPagingSourceOffline()
}
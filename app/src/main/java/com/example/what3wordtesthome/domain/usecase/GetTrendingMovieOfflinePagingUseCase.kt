package com.example.what3wordtesthome.domain.usecase

import androidx.paging.PagingData
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to fetch trending movies from the local database using paging.
 *
 * This is used to support offline access to trending movies that were previously cached.
 *
 * @property repository The [MovieRepository] used to access local data.
 */

class GetTrendingMovieOfflinePagingUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getTrendingPagingSourceOffline()
}
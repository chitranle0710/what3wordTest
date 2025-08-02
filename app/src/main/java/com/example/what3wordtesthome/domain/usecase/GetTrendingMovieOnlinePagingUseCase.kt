package com.example.what3wordtesthome.domain.usecase

import androidx.paging.PagingData
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to fetch movies from the TMDB API based on a search query using paging.
 *
 * This supports online search functionality by delegating the query to the remote API,
 * returning paginated results to improve performance and responsiveness.
 *
 * @property repository The [MovieRepository] responsible for accessing remote movie data.
 */
class GetTrendingMovieOnlinePagingUseCase(private val repository: MovieRepository) {

    /**
     * Invokes the use case to perform a movie search with the given query string.
     *
     * @param id The search query string used to fetch matching movies.
     * @return A [Flow] emitting [PagingData] of [Movie] from the remote source.
     */
    suspend operator fun invoke(id: String): Flow<PagingData<Movie>> =
        repository.searchMoviesPagingOnline(id)
}

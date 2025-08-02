package com.example.what3wordtesthome.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.what3wordtesthome.data.remote.NetworkApi
import com.example.what3wordtesthome.domain.model.Movie

/**
 * A [PagingSource] implementation to fetch paginated movie search results
 * from the TMDB API based on a user-provided query.
 *
 * This enables efficient loading of large lists of movie search results,
 * by requesting data in pages from the remote [NetworkApi].
 *
 * @property api The API client to perform the network search requests.
 * @property query The search query string entered by the user.
 */
class SearchMoviePagingSource(
    private val api: NetworkApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    /**
     * Provides a refresh key to reload the data from a specific position.
     *
     * This implementation always refreshes from the first page.
     *
     * @param state The current paging state including loaded pages and anchor position.
     * @return The key from which to start loading when refreshing.
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

    /**
     * Loads a page of movie data based on the given [params].
     *
     * Calls the API to fetch a page of results for the search [query], then maps
     * the DTOs to [Movie] domain models. If an error occurs during the API call,
     * it returns [LoadResult.Error].
     *
     * @param params The parameters for loading data, including the key (page number).
     * @return A [LoadResult.Page] with movies and pagination keys, or [LoadResult.Error].
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.searchMovies(query = query, page = page)

            LoadResult.Page(
                data = response.results.map { dto ->
                    Movie(
                        adult = dto.adult,
                        backdrop_path = dto.backdrop_path,
                        id = dto.id,
                        title = dto.title,
                        original_title = dto.original_title,
                        overview = dto.overview,
                        poster_path = dto.poster_path,
                        media_type = dto.media_type,
                        original_language = dto.original_language,
                        genre_ids = dto.genre_ids,
                        popularity = dto.popularity,
                        release_date = dto.release_date,
                        video = dto.video,
                        vote_average = dto.vote_average,
                        vote_count = dto.vote_count
                    )
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
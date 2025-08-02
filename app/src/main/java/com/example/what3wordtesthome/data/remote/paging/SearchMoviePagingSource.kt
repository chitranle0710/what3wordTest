package com.example.what3wordtesthome.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.what3wordtesthome.data.remote.NetworkApi
import com.example.what3wordtesthome.domain.model.Movie

class SearchMoviePagingSource(
    private val api: NetworkApi,
    private val query: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }

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
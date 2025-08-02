package com.example.what3wordtesthome.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.what3wordtesthome.data.local.cache.MovieCacheDataStore
import com.example.what3wordtesthome.data.local.dao.MovieDao
import com.example.what3wordtesthome.data.local.entity.TrendingMovieEntity
import com.example.what3wordtesthome.data.remote.NetworkApi
import com.example.what3wordtesthome.data.remote.paging.SearchMoviePagingSource
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.model.MovieDetail
import com.example.what3wordtesthome.domain.model.toDto
import com.example.what3wordtesthome.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MovieRepositoryImpl(
    private val api: NetworkApi,
    private val dao: MovieDao,
    private val cacheDataStore: MovieCacheDataStore
) : MovieRepository {

    private val cacheMutex = Mutex()

    companion object {
        private val CACHE_EXPIRY_MILLIS = TimeUnit.MINUTES.toMillis(60) // 1 hour
    }

    override suspend fun getTrendingMovies(): List<Movie> = cacheMutex.withLock {
        val lastFetch = cacheDataStore.trendingLastFetchTime.first()
        val now = System.currentTimeMillis()
        val useCache = lastFetch != null && (now - lastFetch) < CACHE_EXPIRY_MILLIS

        if (useCache) {
            val cached = dao.getTrending()
            if (cached.isNotEmpty()) {
                return cached.map { dto ->
                    Movie(
                        id = dto.id,
                        title = dto.title,
                        release_date = dto.releaseYear,
                        vote_average = dto.voteAverage
                    )
                }
            }
        }

        val response = api.getTrendingMovies()
        val movies = response.results.map { dto ->
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
        }

        dao.clearTrending()
        dao.insertTrending(movies.map {
            TrendingMovieEntity(
                title = it.title,
                releaseYear = it.release_date,
                id = it.id,
                voteAverage = it.vote_average,
                imageUrl = it.poster_path
            )
        })

        cacheDataStore.setTrendingLastFetchTime(now)
        return movies
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        return api.searchMovies(query).results.map { dto ->
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
        }
    }

    override suspend fun searchMoviesPagingOnline(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviePagingSource(api, query) }
        ).flow
    }

    override suspend fun getTrendingPagingSourceOffline(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { dao.getTrendingPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                Movie(
                    id = entity.id,
                    title = entity.title,
                    release_date = entity.releaseYear,
                    vote_average = entity.voteAverage,
                    poster_path = entity.imageUrl
                )
            }
        }
    }

    override suspend fun getMovieDetail(query: String): MovieDetail = withContext(Dispatchers.IO) {
        val movieId = query.toInt()

        val cached = dao.getMovieDetail(movieId)
        if (cached != null) {
            return@withContext cached.toDto()
        }

        val responseDetailMovie = api.getMovieDetail(movieId)
        val entityTrendingMovie = responseDetailMovie.toEntity()
        dao.insertMovieDetail(entityTrendingMovie)

        return@withContext entityTrendingMovie.toDto()
    }
}

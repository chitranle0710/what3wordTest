package com.example.what3wordtesthome.domain.repository

import com.example.what3wordtesthome.data.local.cache.MovieCacheDataStore
import com.example.what3wordtesthome.data.local.dao.MovieDao
import com.example.what3wordtesthome.data.local.entity.TrendingMovieEntity
import com.example.what3wordtesthome.data.remote.NetworkApi
import com.example.what3wordtesthome.domain.model.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

        // Fetch from API
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

        // Save to DB
        dao.clearTrending()
        dao.insertTrending(movies.map {
            TrendingMovieEntity(
                title = it.title,
                releaseYear = it.release_date,
                id = 0,
                voteAverage = it.vote_average,
                imageUrl = it.poster_path
            )
        })

        // Update cache timestamp
        cacheDataStore.setTrendingLastFetchTime(now)

        return movies
    }
}

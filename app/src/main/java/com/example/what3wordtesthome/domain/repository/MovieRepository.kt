package com.example.what3wordtesthome.domain.repository

import androidx.paging.PagingData
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies(): List<Movie>
    suspend fun getTrendingPagingSourceOffline(): Flow<PagingData<Movie>>
    suspend fun searchMoviesPagingOnline(query: String): Flow<PagingData<Movie>>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetail(query: String): MovieDetail
}
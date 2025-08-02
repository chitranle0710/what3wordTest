package com.example.what3wordtesthome.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.what3wordtesthome.data.local.entity.MovieDetailEntity
import com.example.what3wordtesthome.data.local.entity.TrendingMovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM TrendingMovie")
    suspend fun getTrending(): List<TrendingMovieEntity>

    @Query("SELECT * FROM TrendingMovie")
    fun getTrendingPagingSource(): PagingSource<Int, TrendingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrending(movies: List<TrendingMovieEntity>)

    @Query("DELETE FROM TrendingMovie")
    suspend fun clearTrending()

    @Query("SELECT * FROM MovieDetails WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(detail: MovieDetailEntity)
}
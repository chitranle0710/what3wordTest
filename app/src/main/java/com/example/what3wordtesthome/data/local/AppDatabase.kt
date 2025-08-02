package com.example.what3wordtesthome.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.what3wordtesthome.data.local.dao.MovieDao
import com.example.what3wordtesthome.data.local.entity.MovieDetailEntity
import com.example.what3wordtesthome.data.local.entity.TrendingMovieEntity

@Database(
    entities = [TrendingMovieEntity::class, MovieDetailEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
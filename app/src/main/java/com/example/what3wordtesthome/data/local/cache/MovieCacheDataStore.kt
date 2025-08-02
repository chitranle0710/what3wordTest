package com.example.what3wordtesthome.data.local.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieCacheDataStore(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "movie_cache_prefs")

    private val LAST_FETCH_TIME = longPreferencesKey("last_fetch_time")

    private val dataStore = context.dataStore

    val trendingLastFetchTime: Flow<Long?>
        get() = dataStore.data.map { prefs ->
            prefs[LAST_FETCH_TIME]
        }

    suspend fun setTrendingLastFetchTime(timestamp: Long) {
        dataStore.edit { prefs ->
            prefs[LAST_FETCH_TIME] = timestamp
        }
    }
}
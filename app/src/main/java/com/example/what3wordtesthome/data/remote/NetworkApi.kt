package com.example.what3wordtesthome.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val AUTH_HEADER =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NGIwMTkyNmZhYTg2ODRlMmJiY2ZkZTI4ZTgwNTZkOSIsIm5iZiI6MTc0OTcxNjc2OC4xNDksInN1YiI6IjY4NGE4ZjIwNzBlODY4Yjg4MGIwZDNiNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7DDv7c1TnQJn7KI9gAbBK-8R37JOBt7eQmB9-nqlePM"
    }

    @Headers("Authorization: $AUTH_HEADER")
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): TrendingMoviesResponse

    @Headers("Authorization: $AUTH_HEADER")
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MovieDto
}
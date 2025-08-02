package com.example.what3wordtesthome.di

import androidx.room.Room
import com.example.what3wordtesthome.data.local.AppDatabase
import com.example.what3wordtesthome.data.local.cache.MovieCacheDataStore
import com.example.what3wordtesthome.data.remote.NetworkApi
import com.example.what3wordtesthome.domain.repository.MovieRepository
import com.example.what3wordtesthome.domain.repository.MovieRepositoryImpl
import com.example.what3wordtesthome.domain.usecase.GetTrendingMoviesUseCase
import com.example.what3wordtesthome.domain.usecase.SearchMoviesUseCase
import com.example.what3wordtesthome.presentation.MovieViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "MovieDB"
        ).build()
    }

    single { get<AppDatabase>().movieDao() }

    single {
        Retrofit.Builder()
            .baseUrl(NetworkApi.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)
    }

    single { MovieCacheDataStore(androidApplication()) }

    single<MovieRepository> {
        MovieRepositoryImpl(
            api = get(),
            dao = get(),
            cacheDataStore = get()
        )
    }

    viewModel { MovieViewModel(get(), get()) }

    single {
        GetTrendingMoviesUseCase(
            get(),
        )
    }

    single {
        SearchMoviesUseCase(
            get(),
        )
    }

}
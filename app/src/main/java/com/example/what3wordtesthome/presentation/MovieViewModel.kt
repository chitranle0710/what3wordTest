package com.example.what3wordtesthome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.usecase.GetTrendingMoviesUseCase
import com.example.what3wordtesthome.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<List<Movie>>(emptyList())
    val uiState: StateFlow<List<Movie>> = _uiState.asStateFlow()

    private val _headerTitle = MutableStateFlow("Trending Movies")
    val headerTitle: StateFlow<String> = _headerTitle.asStateFlow()

    init {
        loadTrending()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        viewModelScope.launch {
            if (query.isBlank()) {
                loadTrendingInternal()
            } else {
                _headerTitle.value = "Search Results"
                val result = searchMoviesUseCase(query)
                _uiState.value = result
            }
        }
    }

    fun loadTrending() {
        viewModelScope.launch {
            loadTrendingInternal()
        }
    }

    private suspend fun loadTrendingInternal() {
        _headerTitle.value = "Trending Movies"
        val movies = trendingMoviesUseCase.invoke()
        _uiState.value = movies
    }
}
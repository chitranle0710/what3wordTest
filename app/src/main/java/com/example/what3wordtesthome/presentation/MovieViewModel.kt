package com.example.what3wordtesthome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.usecase.GetTrendingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val useCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<List<Movie>>(emptyList()) // example: list of movie titles
    val uiState: StateFlow<List<Movie>> = _uiState.asStateFlow()

    fun loadMovies() {
        viewModelScope.launch {
            val movies = useCase.invoke()
            _uiState.value = movies
        }
    }
}
package com.example.what3wordtesthome.presentation.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.what3wordtesthome.domain.model.MovieDetail
import com.example.what3wordtesthome.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MovieDetailViewModel(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<MovieDetail>> = _uiState

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = getMovieDetailUseCase.invoke(movieId.toString())
                if (result != null) {
                    _uiState.value = UiState.Success(result)
                } else {
                    _uiState.value = UiState.Error("No movie show")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    if (e is IOException) "You're offline" else "Failed to load movie details"
                )
            }
        }
    }

    init {
        val movieId = savedStateHandle.get<Int>("movieId") ?: -1
        if (movieId != -1) {
            loadMovieDetail(movieId)
        } else {
            _uiState.value = UiState.Error("Invalid movie ID")
        }
    }
}
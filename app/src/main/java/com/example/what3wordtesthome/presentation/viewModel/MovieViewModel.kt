package com.example.what3wordtesthome.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.domain.usecase.GetMovieDetailUseCase
import com.example.what3wordtesthome.domain.usecase.GetTrendingMovieOfflinePagingUseCase
import com.example.what3wordtesthome.domain.usecase.GetTrendingMovieOnlinePagingUseCase
import com.example.what3wordtesthome.domain.usecase.GetTrendingMoviesUseCase
import com.example.what3wordtesthome.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MovieViewModel(
    private val trendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchMoviePagingSource: GetTrendingMovieOnlinePagingUseCase,
    private val offlinePagingUseCase: GetTrendingMovieOfflinePagingUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _headerTitle = MutableStateFlow("Trending Movies")
    val headerTitle: StateFlow<String> = _headerTitle.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _preloadDone = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            trendingMoviesUseCase.getMovieTrending()
            _preloadDone.value = true
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingMoviesFlow: Flow<PagingData<Movie>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                offlinePagingUseCase.invoke()
            } else {
                searchMoviePagingSource.invoke(query)
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _headerTitle.value = if (query.isBlank()) "Trending Movies" else "Search Results"
    }
}
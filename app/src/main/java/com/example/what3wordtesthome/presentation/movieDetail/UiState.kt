package com.example.what3wordtesthome.presentation.movieDetail

/**
 * Represents the UI state of a screen or component.
 *
 * This sealed class is typically used with [kotlinx.coroutines.flow.StateFlow] or [androidx.lifecycle.LiveData] to manage and observe
 * different states like loading, success, and error in a type-safe way.
 *
 * @param T The type of the successful result.
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
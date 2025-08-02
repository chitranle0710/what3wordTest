package com.example.what3wordtesthome.presentation

import MovieDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.what3wordtesthome.domain.model.Movie
import com.example.what3wordtesthome.presentation.viewModel.MovieViewModel
import com.example.what3wordtesthome.ui.theme.What3wordTestHomeTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            What3wordTestHomeTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MovieList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.MovieList.route) {
                            MovieScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate(Screen.MovieDetail.createRoute(movieId))
                                }
                            )
                        }

                        composable(
                            route = Screen.MovieDetail.route,
                            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val movieId =
                                backStackEntry.arguments?.getInt("movieId") ?: return@composable
                            MovieDetailScreen(movieId = movieId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit = {}
) {
    val movies = viewModel.pagingMoviesFlow.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val header by viewModel.headerTitle.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            placeholder = { Text("Search movies...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(header, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                movie?.let {
                    MovieListItem(movie = it, onClick = { onMovieClick(it.id) })
                }
            }

            when (movies.loadState.append) {
                is LoadState.Loading -> item { Text("Loading more...") }
                is LoadState.Error -> item { Text("Error loading more") }
                else -> {}
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w185${movie.poster_path ?: movie.backdrop_path}",
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = movie.title ?: "", style = MaterialTheme.typography.titleMedium)
            Text(text = "Year: ${movie.release_date?.take(4)}")
            Text(text = "Rating: ${movie.vote_average}")
        }
    }
}
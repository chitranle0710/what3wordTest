import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.what3wordtesthome.domain.model.MovieDetail
import com.example.what3wordtesthome.presentation.movieDetail.MovieDetailViewModel
import com.example.what3wordtesthome.presentation.movieDetail.UiState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = getViewModel(parameters = { parametersOf(movieId) })
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    when (state) {
        is UiState.Loading -> {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        is UiState.Error -> {
            Text(
                text = (state as UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        is UiState.Success -> {
            val detail = (state as UiState.Success<MovieDetail>).data
            val imageUrl =
                "https://image.tmdb.org/t/p/w500${detail.posterPath ?: detail.backdropPath}"

            Column(Modifier.padding(16.dp)) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = detail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = detail.title, style = MaterialTheme.typography.titleLarge)
                Text(text = "Genres: ${detail.genres}")
                Text(text = "Release Date: ${detail.releaseDate}")
                Text(text = "Rating: ${detail.rating}")
                Text(text = detail.overview, style = MaterialTheme.typography.bodyMedium)

                if (!detail.homepage.isNullOrBlank()) {
                    Text(
                        text = "View More Details..",
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, detail.homepage.toUri())
                                context.startActivity(intent)
                            }
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}


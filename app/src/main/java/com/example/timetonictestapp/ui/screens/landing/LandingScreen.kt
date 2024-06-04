package com.example.timetonictestapp.ui.screens.landing

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.timetonictestapp.R
import com.example.timetonictestapp.data.BASE_URL_FOR_IMAGES
import com.example.timetonictestapp.data.datamodels.booksmodels.Book
import com.example.timetonictestapp.data.datamodels.booksmodels.BookResponse
import com.example.timetonictestapp.ui.common.ErrorComponent
import com.example.timetonictestapp.ui.common.TopAppBarTimeTonic

@Composable
fun LandingScreen(
    navigateToBookDetail: (BookResponse) -> Unit,
    navigateToLogin: () -> Unit,
    sesskey: String,
    o_u: String,
    landingViewModel: LandingViewModel = viewModel(factory = LandingViewModel.provideFactory())
) {
    // Collect UI state from ViewModel
    val landingUiState by landingViewModel.uiState.collectAsState()

    // Handle back navigation
    BackHandler {
        navigateToLogin()
    }

    // Scaffold with top app bar
    Scaffold(
        topBar = {
            TopAppBarTimeTonic()
        }
    ) {
        // Column layout for the screen content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Check different states of UI
            when {
                // Show loading indicator if data is loading
                landingUiState.isLoading -> {
                    CircularProgressIndicator()
                }
                // Show error message if there is an error
                landingUiState.error.isNotEmpty() -> {
                    ErrorComponent(
                        message = landingUiState.error
                    )
                }
                // Show the list of books if data is available
                else -> {
                    // Spacer
                    Spacer(modifier = Modifier.height(20.dp))
                    // Title
                    Text(
                        text = stringResource(id = R.string.books),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    // Spacer
                    Spacer(modifier = Modifier.height(20.dp))
                    // LazyColumn for the list of books
                    LazyColumn {
                        // Iterate through the list of books
                        items(landingUiState.allBooks.books) { book ->
                            // Spacer
                            Spacer(modifier = Modifier.height(10.dp))
                            // Book card item
                            BookCard(
                                book = book,
                                onClickBook = {
                                    navigateToBookDetail(
                                        BookResponse(
                                            sesskey = sesskey,
                                            o_u = o_u,
                                            b_c = book.b_c
                                        )
                                    )
                                }
                            )
                            // Spacer
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onClickBook: () -> Unit
) {
    // Elevated card for book item
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(150.dp),
        onClick = onClickBook
    ) {
        // Row layout for book item content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Async image for book cover
            AsyncImage(
                model = "$BASE_URL_FOR_IMAGES${book.ownerPrefs.oCoverImg}",
                contentDescription = null,
                error = painterResource(R.drawable.book),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = 16.dp)
            )
            // Column for book details
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Title text with ellipsis
                Text(
                    text = book.ownerPrefs.title ?: stringResource(id = R.string.no_title),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                // Spacer
                Spacer(modifier = Modifier.height(8.dp))
                // Book owner text with ellipsis
                Text(
                    text = book.b_o,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Preview function for LandingScreen
@Preview
@Composable
fun Preview() {
    MaterialTheme {
        ErrorComponent(message = "No image")
    }
}



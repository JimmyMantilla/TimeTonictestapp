package com.example.timetonictestapp.ui.screens.bookinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.timetonictestapp.R
import com.example.timetonictestapp.data.BASE_URL_FOR_IMAGES
import com.example.timetonictestapp.ui.common.ErrorComponent
import com.example.timetonictestapp.ui.common.TopAppBarTimeTonic
import com.example.timetonictestapp.ui.models.BookResume

// Composable function to display the Landing Detail screen
@Composable
fun LandingDetailScreen(
    navigateToLogin: () -> Unit, // Function to navigate to login screen
    navigateUp: () -> Unit, // Function to navigate up in the navigation stack
    landingDetailViewModel: LandingDetailViewModel = viewModel(factory = LandingDetailViewModel.provideFactory())
) {
    // Collect landing detail UI state using ViewModel
    val landingDetailUiState by landingDetailViewModel.uiState.collectAsState()

    // Scaffold to set up the screen layout
    Scaffold(
        topBar = {
            // Display Top App Bar with TimeTonic logo
            TopAppBarTimeTonic()
        }
    ) {
        // Column layout to arrange content vertically
        Column(
            modifier = Modifier.padding(it), // Apply padding
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            // Check different states and display corresponding content
            when {
                landingDetailUiState.isLoading -> {
                    // Show CircularProgressIndicator if loading
                    CircularProgressIndicator()
                }
                landingDetailUiState.error.isNotEmpty() -> {
                    // Show ErrorComponent if there is an error
                    ErrorComponent(
                        message = landingDetailUiState.error
                    )
                }
                else -> {
                    // Show BookDetail if no loading or error
                    BookDetail(book = landingDetailUiState.book)
                }
            }
        }
    }
}

// Composable function to display detailed information of a book
@Composable
fun BookDetail(
    book: BookResume, // Book details
) {
    // Scaffold to set up the screen layout
    Scaffold(
        topBar = {
            // Display top bar with book title
            Text(
                text = book.title ?: "There is not title for this book",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier.padding(16.dp) // Apply padding
    ) {
        // LazyColumn to lazily load content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Occupy maximum available space
                .padding(it) // Apply padding
        ) {
            // Construct the LazyColumn item
            val imageUrl = "$BASE_URL_FOR_IMAGES${book.image}"
            item {
                // Column layout to arrange content vertically
                Column(
                    modifier = Modifier.padding(20.dp) // Apply padding
                ) {
                    // Box to display rounded image with description title
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Occupy maximum available width
                            .height(300.dp) // Set fixed height
                            .clip(MaterialTheme.shapes.medium) // Clip to medium rounded shape
                    ) {
                        // AsyncImage to load and display the book image
                        AsyncImage(
                            model = imageUrl, // Image URL
                            contentDescription = null,
                            error = painterResource(R.drawable.book), // Default error image
                            modifier = Modifier.fillMaxSize(), // Occupy maximum available space
                            contentScale = ContentScale.Crop // Crop the image to fit the box
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp)) // Add space below the image
                    // Description title
                    Text(
                        text = stringResource(id = R.string.description), // Description title
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium, // Title style
                        modifier = Modifier
                            .padding(bottom = 8.dp) // Apply bottom padding
                    )
                    // Description text
                    Text(
                        text = book.description ?: "There is not description for this book!",
                        textAlign = TextAlign.Justify, // Justify text alignment
                        style = MaterialTheme.typography.bodyMedium // Body text style
                    )
                    Spacer(modifier = Modifier.height(20.dp)) // Add space between description and owner
                    // Book owner information
                    Text(
                        text = "Book Owner:\n"+book.owner ?: "There is not owner for this book",
                        textAlign = TextAlign.Justify, // Justify text alignment
                        style = MaterialTheme.typography.titleMedium // Title text style
                    )
                }
            }
        }
    }
}



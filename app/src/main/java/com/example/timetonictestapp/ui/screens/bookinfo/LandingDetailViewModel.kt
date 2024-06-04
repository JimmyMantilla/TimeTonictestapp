package com.example.timetonictestapp.ui.screens.bookinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.timetonictestapp.data.RetrofitServiceFactory
import com.example.timetonictestapp.data.repository.books.BooksRepositoryImpl
import com.example.timetonictestapp.domain.landing.BooksRepository
import com.example.timetonictestapp.ui.models.BookResume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class representing the state of the Landing Detail screen
data class LandingDetailUiState(
    val isLoading: Boolean = false, // Loading indicator
    val book: BookResume = BookResume(title = "", image = "", owner = "", description = ""), // Book details
    val error: String = "" // Error message
)

// ViewModel class for the Landing Detail screen
class LandingDetailViewModel(
    savedStateHandle: SavedStateHandle, // Saved state handle
    private val bookDetailRepository: BooksRepository // Repository for fetching book details
) : ViewModel() {

    // Mutable state flow to hold the UI state
    private val _uiState: MutableStateFlow<LandingDetailUiState> =
        MutableStateFlow(LandingDetailUiState())
    // State flow to expose UI state
    val uiState: StateFlow<LandingDetailUiState> = _uiState.asStateFlow()

    // Initialize ViewModel
    init {
        // Retrieve session key, user ID, and book code from saved state handle
        val sesskey: String = checkNotNull(savedStateHandle["sesskey"])
        val o_u: String = checkNotNull(savedStateHandle["o_u"])
        val b_c: String = checkNotNull(savedStateHandle["b_c"])
        // Fetch book details
        getBookInfo(sesskey, o_u, b_c)
    }

    // Function to fetch book details
    private fun getBookInfo(sesskey: String, o_u: String, b_c: String) {
        // Update UI state to show loading indicator
        _uiState.update { it.copy(isLoading = true) }

        // Launch a coroutine in the viewModelScope
        viewModelScope.launch {
            try {
                // Call repository to fetch book details
                bookDetailRepository.getBookInfo(sesskey = sesskey, o_u = o_u, b_c = b_c)
                    .collect { book ->
                        // Map book details to BookResume object
                        val bookResume = BookResume(
                            title = book.ownerPrefs.title,
                            image = book.ownerPrefs.oCoverImg,
                            owner = book.b_o,
                            description = book.description
                        )
                        // Update UI state with book details and hide loading indicator
                        _uiState.update { it.copy(isLoading = false, book = bookResume) }
                    }
            } catch (e: Exception) {
                // Handle network request failure
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Network request failed. Try again. \n${e.message}"
                    )
                }
            }
        }
    }

    // Companion object for providing ViewModel factory
    companion object {
        // Function to provide ViewModel factory
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            // Create ViewModel instance
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val savedStateHandle = extras.createSavedStateHandle()

                // Create and return LandingDetailViewModel instance
                return LandingDetailViewModel(
                    savedStateHandle,
                    BooksRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())
                ) as T
            }
        }
    }
}


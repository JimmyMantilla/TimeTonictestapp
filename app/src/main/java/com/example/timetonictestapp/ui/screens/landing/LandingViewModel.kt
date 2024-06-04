package com.example.timetonictestapp.ui.screens.landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.timetonictestapp.data.RetrofitServiceFactory
import com.example.timetonictestapp.data.datamodels.booksmodels.AllBooks
import com.example.timetonictestapp.data.repository.books.BooksRepositoryImpl
import com.example.timetonictestapp.domain.landing.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LandingUiState(
    val isLoading: Boolean = false,
    val allBooks: AllBooks = AllBooks(
        books = emptyList(),
        contacts = emptyList(),
        nbBooks = 0,
        nbContacts = 0
    ),
    val error: String = ""
)

class LandingViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BooksRepository
) : ViewModel() {

    // Mutable state flow for UI state
    private val _uiState: MutableStateFlow<LandingUiState> = MutableStateFlow(LandingUiState())
    // State flow for UI state
    val uiState: StateFlow<LandingUiState> = _uiState.asStateFlow()

    init {
        // Retrieve session key and user ID from saved state handle
        val sesskey: String = checkNotNull(savedStateHandle["sesskey"])
        val o_u: String = checkNotNull(savedStateHandle["o_u"])
        // Fetch all books
        getAllBooks(sesskey, o_u)
    }

    // Function to fetch all books
    private fun getAllBooks(sesskey: String, o_u: String) {
        // Update UI state to indicate loading
        _uiState.update { it.copy(isLoading = true) }

        // Asynchronous call to fetch books from repository
        viewModelScope.launch {
            try {
                // Collect books from repository
                bookRepository.getAllBooks(sesskey = sesskey, o_u = o_u).collect { allBooks ->
                    // Update UI state with fetched books
                    _uiState.update {
                        it.copy(isLoading = false, allBooks = allBooks)
                    }
                }
            } catch (e: Exception) {
                // Update UI state in case of error
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Network request failed. Try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        // Factory method to create LandingViewModel
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val savedStateHandle = extras.createSavedStateHandle()

                return LandingViewModel(
                    savedStateHandle,
                    BooksRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())
                ) as T
            }
        }
    }
}

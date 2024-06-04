package com.example.timetonictestapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.timetonictestapp.data.RetrofitServiceFactory
import com.example.timetonictestapp.data.datamodels.loginmodels.LoginResponse
import com.example.timetonictestapp.data.repository.login.LoginRepositoryImpl
import com.example.timetonictestapp.domain.login.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse = LoginResponse(o_u = "", sesskey = ""),
    val error: String = ""
)

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Function to perform login
    fun login(username: String, password: String) {
        // Update UI state to show loading indicator
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // Perform login and collect the login response
                repository.login(username, password).collect { loginResponse ->
                    // Update UI state with the received login response
                    _uiState.update { it.copy(isLoading = false, loginResponse = loginResponse) }
                }
            } catch (e: Exception) {
                // Update UI state in case of error
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Network request failed, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        // Factory method to create ViewModel instance
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(LoginRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())) as T
            }
        }
    }
}

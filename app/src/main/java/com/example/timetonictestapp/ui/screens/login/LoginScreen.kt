package com.example.timetonictestapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timetonictestapp.R
import com.example.timetonictestapp.data.datamodels.loginmodels.LoginResponse

@Composable
fun LoginScreen(
    navigateToLandingPage: (LoginResponse) -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.provideFactory())
) {
    // Collect login UI state
    val loginUiState by loginViewModel.uiState.collectAsState()

    // Display different UI components based on login UI state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            loginUiState.isLoading -> {
                // Show loading indicator
                CircularProgressIndicator()
            }

            // If login is successful, navigate to landing page
            loginUiState.loginResponse.o_u.isNotEmpty() && loginUiState.loginResponse.sesskey.isNotEmpty() -> {
                navigateToLandingPage(loginUiState.loginResponse)
            }

            else -> {
                // Show login form
                LoginForm(
                    errorMessageFromServer = loginUiState.error,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}

@Composable
fun LoginForm(
    errorMessageFromServer: String,
    loginViewModel: LoginViewModel
) {
    // State variables for username, password, error message, and password visibility
    var username by rememberSaveable { mutableStateOf("android.developer@timetonic.com") }
    var password by rememberSaveable { mutableStateOf("Android.developer1") }
    var errorMessage by rememberSaveable { mutableStateOf(errorMessageFromServer) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // State variables for empty username and password fields
    var isUsernameEmpty by rememberSaveable { mutableStateOf(false) }
    var isPasswordEmpty by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // TimeTonic logo
        Image(
            painter = painterResource(id = R.drawable.timetonic),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        // Login message
        Text(
            text = stringResource(id = R.string.login_msg),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Username text field
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isUsernameEmpty = it.isEmpty()
            },
            label = { Text(text = stringResource(id = R.string.user_label)) },
            supportingText = {
                if (isUsernameEmpty) {
                    Text(text = stringResource(id = R.string.login_input_empty))
                }
            },
            isError = isUsernameEmpty,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Password text field
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordEmpty = it.isEmpty()
            },
            label = { Text(text = stringResource(id = R.string.password_label)) },
            supportingText = {
                if (isPasswordEmpty) {
                    Text(text = stringResource(id = R.string.login_input_empty))
                }
            },
            isError = isPasswordEmpty,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image: ImageVector = if (isPasswordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(imageVector = image, contentDescription = if (isPasswordVisible) "Hide password" else "Show password")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Login button
        Button(
            onClick = {
                errorMessage = ""

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    isUsernameEmpty = false
                    isPasswordEmpty = false

                    loginViewModel.login(username = username, password = password)
                } else {
                    isUsernameEmpty = username.isEmpty()
                    isPasswordEmpty = password.isEmpty()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.login_button))
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Display error message if present
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen({})
    }
}

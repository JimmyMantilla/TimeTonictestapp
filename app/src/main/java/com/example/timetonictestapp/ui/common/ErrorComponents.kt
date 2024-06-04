package com.example.timetonictestapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Composable function to display an error component
@Composable
fun ErrorComponent(message: String) {
    // Column layout to center the content vertically and horizontally
    Column(
        modifier = Modifier
            .fillMaxSize() // Occupy maximum available space
            .padding(16.dp), // Apply padding
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        // Icon to indicate error
        Icon(
            imageVector = Icons.Default.NoAccounts, // Default error icon
            contentDescription = "Error", // Description for accessibility
            modifier = Modifier.size(200.dp), // Set size of the icon
            tint = MaterialTheme.colorScheme.error // Set tint color
        )
        // Spacer to add space between icon and text
        Spacer(modifier = Modifier.height(10.dp))
        // Text to display the error message
        Text(
            text = message, // Error message
            textAlign = TextAlign.Center // Center align the text
        )
    }
}




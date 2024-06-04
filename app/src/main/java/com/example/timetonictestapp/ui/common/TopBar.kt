package com.example.timetonictestapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.timetonictestapp.R

// Composable function to display the top app bar with TimeTonic logo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarTimeTonic() {
    // TopAppBar with TimeTonic logo as title
    TopAppBar(
        title = {
            // Image displaying TimeTonic logo
            Image(
                painter = painterResource(id = R.drawable.timetonic), // Image resource
                contentDescription = "Topbar", // Description for accessibility
                modifier = Modifier.size(200.dp) // Set size of the image
            )
        }
    )
}

package com.martinm.backseatalert.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(Modifier.padding(24.dp)) {
        Text("Detection is ${if (uiState.isEnabled) "Enabled" else "Disabled"}")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate("settings") }) {
            Text("Go to Settings")
        }
    }
}
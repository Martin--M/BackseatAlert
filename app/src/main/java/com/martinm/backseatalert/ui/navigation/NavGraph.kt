package com.martinm.backseatalert.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.martinm.backseatalert.ui.main.MainScreen
import com.martinm.backseatalert.ui.settings.SettingsScreen

@Composable
fun BackseatAlertApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
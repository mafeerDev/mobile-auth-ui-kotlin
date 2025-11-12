package com.example.examen_app.ui.screen.container

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenContainer() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}
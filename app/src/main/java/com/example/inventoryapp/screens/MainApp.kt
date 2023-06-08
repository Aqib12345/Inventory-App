package com.example.inventoryapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.inventoryapp.navigation.AppNavHost

@Composable
fun MainApp() {
    val navController = rememberNavController()
    AppNavHost(navController = navController , modifier = Modifier)
}
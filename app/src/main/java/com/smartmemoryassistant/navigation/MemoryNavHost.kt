package com.smartmemoryassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartmemoryassistant.presentation.home.HomeScreen
import com.smartmemoryassistant.presentation.home.HomeScreen1
import com.smartmemoryassistant.presentation.splash.SplashScreen

@Composable
fun MemoryNavHost(
    navController: NavHostController,
    startDestination: String,
    onSplashFinished: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MemoryRoute.Splash.route) {
            SplashScreen(onFinished = onSplashFinished)
        }
        composable(MemoryRoute.Home.route) {
            HomeScreen1()
        }
    }
}


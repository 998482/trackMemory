package com.smartmemoryassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.smartmemoryassistant.navigation.MemoryNavHost
import com.smartmemoryassistant.navigation.MemoryRoute
import com.smartmemoryassistant.ui.theme.SmartMemoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var keepSystemSplash = true
        installSplashScreen().setKeepOnScreenCondition { keepSystemSplash }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartMemoryTheme {
                SmartMemoryApp(
                    onAppReady = { keepSystemSplash = false }
                )
            }
        }
    }
}

@Composable
private fun SmartMemoryApp(
    onAppReady: () -> Unit
) {
    val navController = rememberNavController()
    var startRoute by remember { mutableStateOf(MemoryRoute.Splash.route) }

    MemoryNavHost(
        navController = navController,
        startDestination = startRoute,
        onSplashFinished = {
            startRoute = MemoryRoute.Home.route
            onAppReady()
            navController.navigate(MemoryRoute.Home.route) {
                popUpTo(MemoryRoute.Splash.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    )
}


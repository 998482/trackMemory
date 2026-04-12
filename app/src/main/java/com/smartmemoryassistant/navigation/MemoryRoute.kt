package com.smartmemoryassistant.navigation

sealed class MemoryRoute(val route: String) {
    data object Splash : MemoryRoute("splash")
    data object Home : MemoryRoute("home")
}


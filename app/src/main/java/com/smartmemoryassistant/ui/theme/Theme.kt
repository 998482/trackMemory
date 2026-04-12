package com.smartmemoryassistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SmartMemoryColors = darkColorScheme(
    primary = AccentRed,
    secondary = AccentBlue,
    tertiary = Color(0xFFFFB86B),
    background = AppBackground,
    surface = PanelDark,
    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun SmartMemoryTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SmartMemoryColors,
        typography = SmartMemoryTypography,
        content = content
    )
}

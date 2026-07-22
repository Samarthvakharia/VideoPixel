package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NetflixColorScheme = darkColorScheme(
    primary = NetflixRed,
    secondary = NetflixBlue,
    tertiary = SecondaryDark,
    background = CinematicBlack,
    surface = DarkSurface,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = SecondaryDark,
    onSurfaceVariant = TextMuted
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force cinematic dark theme for Netflix
    dynamicColor: Boolean = false, // Disable dynamic colors to preserve Netflix brand colors
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = NetflixColorScheme,
        typography = Typography,
        content = content
    )
}

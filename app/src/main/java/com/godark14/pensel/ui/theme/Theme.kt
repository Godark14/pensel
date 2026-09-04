package com.godark14.pensel.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PenselDarkColorScheme = darkColorScheme(
    primary = PenselPurple,
    onPrimary = PenselTextPrimary,
    secondary = PenselPurpleDark,
    background = PenselBackground,
    onBackground = PenselTextPrimary,
    surface = PenselSurface,
    onSurface = PenselTextPrimary,
    surfaceVariant = PenselSurfaceVariant,
    onSurfaceVariant = PenselTextSecondary,
    outline = PenselBorder,
    error = PenselError
)

private val PenselLightColorScheme = lightColorScheme(
    primary = PenselPurple,
    onPrimary = PenselLightSurface,
    secondary = PenselPurpleDark,
    background = PenselLightBackground,
    onBackground = PenselLightTextPrimary,
    surface = PenselLightSurface,
    onSurface = PenselLightTextPrimary,
    surfaceVariant = PenselLightSurfaceVariant,
    onSurfaceVariant = PenselLightTextSecondary,
    outline = PenselLightBorder,
    error = PenselError
)

@Composable
fun PenselTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PenselDarkColorScheme else PenselLightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PizzaPrimary,
    onPrimary = PizzaOnPrimary,
    primaryContainer = PizzaPrimaryContainer,
    onPrimaryContainer = PizzaOnPrimary,
    secondary = PizzaSecondary,
    onSecondary = PizzaOnPrimary,
    secondaryContainer = PizzaSecondaryContainer,
    onSecondaryContainer = PizzaOnSecondaryContainer,
    tertiary = PizzaTertiary,
    onTertiary = PizzaOnTertiary,
    tertiaryContainer = PizzaTertiaryContainer,
    onTertiaryContainer = PizzaOnTertiary,
    background = PizzaBackground,
    onBackground = PizzaOnSurface,
    surface = PizzaSurface,
    onSurface = PizzaOnSurface,
    surfaceVariant = PizzaSurfaceContainerHighest,
    onSurfaceVariant = PizzaOnSurfaceVariant,
    outline = PizzaOutline,
    outlineVariant = PizzaOutlineVariant,
    inverseSurface = PizzaInverseSurface,
    inverseOnSurface = PizzaInverseOnSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = PizzaPrimaryContainer,
    onPrimary = PizzaOnPrimary,
    primaryContainer = PizzaPrimary,
    onPrimaryContainer = PizzaOnPrimary,
    secondary = PizzaSecondaryContainer,
    onSecondary = PizzaOnSecondaryContainer,
    surface = PizzaInverseSurface,
    onSurface = PizzaInverseOnSurface,
    background = PizzaInverseSurface,
    onBackground = PizzaInverseOnSurface
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

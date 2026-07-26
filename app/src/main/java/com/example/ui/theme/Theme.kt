package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = FestForgeAccent,
    secondary = FestForgePrimaryContainer,
    tertiary = FestForgeSuccessGreen,
    background = DarkFestForgeBackground,
    surface = DarkFestForgeSurface,
    onPrimary = DarkFestForgeBackground,
    onSecondary = DarkFestForgeBackground,
    onBackground = FestForgeSurface,
    onSurface = FestForgeSurface
  )

private val LightColorScheme =
  lightColorScheme(
    primary = FestForgePrimary,
    onPrimary = FestForgeOnPrimary,
    primaryContainer = FestForgePrimaryContainer,
    onPrimaryContainer = FestForgeOnPrimaryContainer,
    secondary = FestForgeSecondary,
    secondaryContainer = FestForgeSecondaryContainer,
    tertiary = FestForgeTertiary,
    tertiaryContainer = FestForgeTertiaryContainer,
    background = FestForgeBackground,
    surface = FestForgeSurface,
    surfaceVariant = FestForgeSurfaceVariant,
    outline = FestForgeOutline,
    onBackground = Color(0xFF1D1B20),
    onSurface = Color(0xFF1D1B20)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

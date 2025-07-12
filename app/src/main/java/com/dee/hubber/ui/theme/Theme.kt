package com.dee.hubber.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp





private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary =  Color(0xE6000000),
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),

    // Secondary colors
    secondary = Color(0xFF00BCD4),
    onSecondary = Color(0xFF003544),
    secondaryContainer = Color(0xFF004D61),
    onSecondaryContainer = Color(0xFFB8EAFF),

    // Tertiary colors
    tertiary = Pink80,
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),

    // Error colors
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Background colors
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),

    // Surface colors
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Black80,
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainerLowest = Color(0xFF0F0D13),

    // Inverse colors
    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF313033),
    inversePrimary = Color(0xFF6650A4),

    // Outline colors
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),

    // Other colors
    scrim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary =  Color(0xE6FFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),

    // Secondary colors
    secondary = Color(0xFF00BCD4),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFB8EAFF),
    onSecondaryContainer = Color(0xFF001F25),

    // Tertiary colors
    tertiary = Pink40,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD8E4),
    onTertiaryContainer = Color(0xFF31111D),

    // Error colors
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    // Background colors
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),

    // Surface colors
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    surfaceTint = Black40,
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerHigh = Color(0xFFECE6F0),
    surfaceContainerHighest = Color(0xFFE6E0E9),
    surfaceContainerLow = Color(0xFFF9F2FC),
    surfaceContainerLowest = Color(0xFFFFFFFF),

    // Inverse colors
    inverseSurface = Color(0xFF313033),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFD0BCFF),

    // Outline colors
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),

    // Other colors
    scrim = Color(0xFF000000)
)

@Composable
fun HubberTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(
            small = RoundedCornerShape(2.dp),
            medium = RoundedCornerShape(4.dp),
            large = RoundedCornerShape(8.dp),
            extraLarge = RoundedCornerShape(16.dp),
        ),
        content = content
    )
}
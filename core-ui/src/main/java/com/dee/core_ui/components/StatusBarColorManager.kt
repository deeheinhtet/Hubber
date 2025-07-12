
package com.dee.core_ui.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun StatusBarColorManager(
    statusBarColor: Color = MaterialTheme.colorScheme.background,
    useDarkIcons: Boolean = !isSystemInDarkTheme()
) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        window.statusBarColor = statusBarColor.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkIcons
    }
}
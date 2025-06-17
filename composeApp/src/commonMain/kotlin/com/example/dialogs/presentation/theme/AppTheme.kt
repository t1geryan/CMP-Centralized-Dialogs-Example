package com.example.dialogs.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    spacing: Spacing = MaterialTheme.spacing,
    opacity: Opacity = MaterialTheme.opacity,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalSpacing provides spacing,
        LocalOpacity provides opacity,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

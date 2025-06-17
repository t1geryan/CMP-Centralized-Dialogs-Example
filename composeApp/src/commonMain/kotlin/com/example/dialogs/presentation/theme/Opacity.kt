package com.example.dialogs.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class Opacity(
    val transparent: Float = 0.0f,
    val almostTransparent: Float = 0.15f,
    val faintlyVisible: Float = 0.35f,
    val semiTransparent: Float = 0.5f,
    val slightlyTransparent: Float = 0.65f,
    val almostOpaque: Float = 0.85f,
    val opaque: Float = 1.0f,
)

val LocalOpacity = staticCompositionLocalOf { Opacity() }

val MaterialTheme.opacity: Opacity
    @Composable
    @ReadOnlyComposable
    get() = LocalOpacity.current

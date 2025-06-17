package com.example.dialogs.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val zero: Dp = 0.dp,
    val extraTiny: Dp = 2.dp,
    val tiny: Dp = 4.dp,
    val little: Dp = 6.dp,
    val extraSmall: Dp = 8.dp,
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val secondaryNormal: Dp = 20.dp,
    val big: Dp = 24.dp,
    val large: Dp = 32.dp,
    val huge: Dp = 40.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

package com.example.dialogs.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Purple80 = Color(0xFFD0BCFF)
internal val PurpleGrey80 = Color(0xFFCCC2DC)
internal val Pink80 = Color(0xFFEFB8C8)
internal val LightGrey = Color(0xFFDFDFDF)
internal val White = Color(0xFFFFFF)

internal val Purple40 = Color(0xFF6650a4)
internal val PurpleGrey40 = Color(0xFF625b71)
internal val Pink40 = Color(0xFF7D5260)
internal val DarkGrey = Color(0xFF3B3B3B)

internal val darkColorScheme = darkColorScheme(
    primary = Purple80,
    primaryContainer = Pink80,
    secondary = PurpleGrey80,
    surface = DarkGrey,
)

internal val lightColorScheme = lightColorScheme(
    primary = Purple40,
    primaryContainer = Pink40,
    secondary = PurpleGrey40,
    surface = LightGrey,
    background = White,
)

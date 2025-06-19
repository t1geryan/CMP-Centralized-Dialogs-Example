package com.example.dialogs.presentation.dialogs

import androidx.compose.runtime.staticCompositionLocalOf

val LocalDialogHolder = staticCompositionLocalOf<DialogHolder> {
    throw IllegalStateException("There is no DialogHolder in composable tree")
}

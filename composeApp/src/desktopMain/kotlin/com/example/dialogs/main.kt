package com.example.dialogs

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.dialogs.presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DialogsExample",
    ) {
        App()
    }
}
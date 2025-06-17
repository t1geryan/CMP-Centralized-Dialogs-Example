package com.example.dialogs

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.dsr.cmp.common.utils.runOnUiThread
import com.example.dialogs.di.commonModules
import com.example.dialogs.presentation.App
import com.example.dialogs.presentation.features.root.DefaultRootComponent
import org.koin.core.context.startKoin

fun main() {
    val lifecycle = LifecycleRegistry()

    val rootComponent = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle)
        )
    }

    startKoin {
        modules(commonModules)
    }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "DialogsExample",
        ) {
            App(rootComponent = rootComponent)
        }
    }
}
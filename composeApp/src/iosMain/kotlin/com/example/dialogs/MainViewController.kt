package com.example.dialogs

import androidx.compose.ui.window.ComposeUIViewController
import com.example.dialogs.di.commonModules
import com.example.dialogs.presentation.App
import com.example.dialogs.presentation.features.root.RootComponent
import org.koin.core.context.startKoin

fun MainViewController(rootComponent: RootComponent) = ComposeUIViewController {
    App(rootComponent = rootComponent)
}

fun doInitKoin() {
    startKoin {
        modules(commonModules)
    }
}
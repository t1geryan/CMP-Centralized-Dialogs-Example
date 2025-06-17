package com.example.dialogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.example.dialogs.presentation.App
import com.example.dialogs.presentation.features.root.DefaultRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val rootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            onMinimize = { moveTaskToBack(true) },
        )
        setContent {
            App(rootComponent = rootComponent)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
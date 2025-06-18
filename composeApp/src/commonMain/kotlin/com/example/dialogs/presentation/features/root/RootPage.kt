package com.example.dialogs.presentation.features.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.dialogs.presentation.features.login.LoginPage
import com.example.dialogs.presentation.features.main.MainPage
import com.example.dialogs.presentation.features.welcome.WelcomePage

@Composable
fun RootPage(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade()),
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Login -> LoginPage(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.Welcome -> WelcomePage(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootComponent.Child.Main -> MainPage(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

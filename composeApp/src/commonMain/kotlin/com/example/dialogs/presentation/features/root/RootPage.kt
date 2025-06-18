package com.example.dialogs.presentation.features.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dialogs.presentation.dialogs.confirmation.ConfirmationComponent
import com.example.dialogs.presentation.dialogs.confirmation.ConfirmationPane
import com.example.dialogs.presentation.dialogs.info.InfoDialogComponent
import com.example.dialogs.presentation.dialogs.info.InfoDialogPane
import com.example.dialogs.presentation.dialogs.slider.BottomSliderComponent
import com.example.dialogs.presentation.dialogs.slider.BottomSliderPane
import com.example.dialogs.presentation.dialogs.toast.ToastComponent
import com.example.dialogs.presentation.dialogs.toast.ToastPane
import com.example.dialogs.presentation.features.login.LoginPage
import com.example.dialogs.presentation.features.main.MainPage
import com.example.dialogs.presentation.features.welcome.WelcomePage

@Composable
fun RootPage(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val dialogSlot by component.dialog.subscribeAsState()

    Box(modifier = modifier) {
        Children(
            stack = component.childStack,
            animation = stackAnimation(fade()),
            modifier = Modifier.fillMaxSize(),
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

        when (val dialogComponent = dialogSlot.child?.instance) {
            is ToastComponent -> ToastPane(
                component = dialogComponent,
                modifier = Modifier.fillMaxSize(),
            )

            is InfoDialogComponent -> InfoDialogPane(
                component = dialogComponent,
            )

            is ConfirmationComponent -> ConfirmationPane(
                component = dialogComponent,
            )

            is BottomSliderComponent -> BottomSliderPane(
                component = dialogComponent,
            )
        }
    }
}

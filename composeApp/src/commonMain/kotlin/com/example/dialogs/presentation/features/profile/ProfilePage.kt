package com.example.dialogs.presentation.features.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.example.dialogs.presentation.features.first.FirstPage
import com.example.dialogs.presentation.features.second.SecondPage
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.profile_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfilePage(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    val stack by component.childStack.subscribeAsState()

    ProfilePage(childStack = stack, modifier = modifier)
}

@Composable
fun ProfilePage(
    childStack: ChildStack<*, ProfileComponent.Child>,
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.profile_page_title),
        modifier = modifier,
    ) { paddingValues ->
        Children(
            stack = childStack,
            animation = stackAnimation(fade()),
            modifier = Modifier.padding(paddingValues),
        ) {
            when (val child = it.instance) {
                is ProfileComponent.Child.First -> FirstPage(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )

                is ProfileComponent.Child.Second -> SecondPage(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
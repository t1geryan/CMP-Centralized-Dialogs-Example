package com.example.dialogs.presentation.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.example.dialogs.presentation.features.profile.ProfilePage
import com.example.dialogs.presentation.features.settings.SettingsPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.main_tabs_profile
import dialogsexample.composeapp.generated.resources.main_tabs_settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainPage(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    val stack by component.childStack.subscribeAsState()

    MainPage(
        stack = stack,
        onTabSelected = component::onTabSelected,
        modifier = modifier,
    )
}

@Composable
fun MainPage(
    stack: ChildStack<*, MainComponent.Child>,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Children(
            stack = stack,
            animation = stackAnimation(fade()),
            modifier = Modifier.fillMaxWidth().weight(1.0f),
        ) {
            when (val child = it.instance) {
                is MainComponent.Child.Profile -> ProfilePage(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
                )

                is MainComponent.Child.Settings -> SettingsPage(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        BottomAppBar(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
        ) {
            MainTab.entries.forEach { tab ->
                NavigationBarItem(
                    label = { MainTabLabel(tab) },
                    icon = { MainTabIcon(tab) },
                    selected = MainTab.getByChild(stack.active.instance) == tab,
                    onClick = { onTabSelected(tab) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    )
                )
            }
        }
    }
}

@Composable
private fun MainTabLabel(tab: MainTab) {
    Text(
        text = stringResource(
            when (tab) {
                MainTab.PROFILE -> Res.string.main_tabs_profile
                MainTab.SETTINGS -> Res.string.main_tabs_settings
            }
        ),
    )
}

@Composable
private fun MainTabIcon(tab: MainTab) {
    Icon(
        imageVector = when (tab) {
            MainTab.PROFILE -> Icons.Default.Person
            MainTab.SETTINGS -> Icons.Default.Settings
        },
        contentDescription = null,
    )
}
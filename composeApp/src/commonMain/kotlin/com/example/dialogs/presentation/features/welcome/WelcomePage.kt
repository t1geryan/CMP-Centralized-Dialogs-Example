package com.example.dialogs.presentation.features.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.welcome_page_start_button_title
import dialogsexample.composeapp.generated.resources.welcome_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun WelcomePage(
    component: WelcomeComponent,
    modifier: Modifier = Modifier,
) {
    WelcomePage(onStartClicked = component::onStartClicked, modifier = modifier)
}

@Composable
fun WelcomePage(
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.welcome_page_title),
        modifier = modifier,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            Button(
                onClick = onStartClicked,
                modifier = Modifier.align(Alignment.Center),
            ) {
                Text(text = stringResource(Res.string.welcome_page_start_button_title))
            }
        }
    }
}
package com.example.dialogs.presentation.features.login

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
import dialogsexample.composeapp.generated.resources.login_page_login_button_title
import dialogsexample.composeapp.generated.resources.login_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginPage(
    component: LoginComponent,
    modifier: Modifier = Modifier,
) {
    LoginPage(
        onNavigateBack = component.onNavigateBack,
        onLoginClicked = component::onLoginClicked,
        modifier = modifier,
    )
}

@Composable
fun LoginPage(
    onNavigateBack: () -> Unit,
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.login_page_title),
        onNavigateBack = onNavigateBack,
        modifier = modifier,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            Button(
                onClick = onLoginClicked,
                modifier = Modifier.align(Alignment.Center),
            ) {
                Text(text = stringResource(Res.string.login_page_login_button_title))
            }
        }
    }
}

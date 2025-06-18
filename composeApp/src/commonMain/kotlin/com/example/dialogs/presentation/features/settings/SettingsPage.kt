package com.example.dialogs.presentation.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.settings_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsPage(
    component: SettingsComponent,
    modifier: Modifier = Modifier,
) {
    SettingsPage(modifier = modifier)
}

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.settings_page_title),
        modifier = modifier,
    ) {

    }
}
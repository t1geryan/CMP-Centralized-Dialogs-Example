package com.example.dialogs.presentation.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterPane
import com.example.dialogs.presentation.widgets.scaffold.AppPage
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.settings_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsPage(
    component: SettingsComponent,
    modifier: Modifier = Modifier,
) {
    SettingsPage(
        dialogPresenterComponent = component.dialogsPresenter,
        modifier = modifier,
    )
}

@Composable
fun SettingsPage(
    dialogPresenterComponent: DialogsPresenterComponent,
    modifier: Modifier = Modifier,
) {
    AppPage(
        title = stringResource(Res.string.settings_page_title),
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {
            DialogsPresenterPane(
                component = dialogPresenterComponent,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
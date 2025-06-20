package com.example.dialogs.presentation.features.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterPane
import com.example.dialogs.presentation.widgets.spacing.Expanded
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.second_page_navigate_third_button_title
import dialogsexample.composeapp.generated.resources.second_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SecondPage(
    component: SecondComponent,
    modifier: Modifier = Modifier,
) {
    SecondPage(
        dialogPresenterComponent = component.dialogsPresenter,
        onNavigateNextClicked = component::onNavigateNextClicked,
        modifier = modifier,
    )
}

@Composable
fun SecondPage(
    dialogPresenterComponent: DialogsPresenterComponent,
    onNavigateNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(Res.string.second_page_title),
        )
        Button(onClick = onNavigateNextClicked) {
            Text(
                text = stringResource(Res.string.second_page_navigate_third_button_title)
            )
        }
        Expanded()
        DialogsPresenterPane(
            component = dialogPresenterComponent,
        )
    }
}
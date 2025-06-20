package com.example.dialogs.presentation.features.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dialogs.presentation.widgets.spacing.Spacer
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.common_percent
import dialogsexample.composeapp.generated.resources.dialogs_presenter_show_confirmation_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_show_info_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_show_slider_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_show_toast_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_slider_selected_value
import org.jetbrains.compose.resources.stringResource

@Composable
fun DialogsPresenterPane(
    component: DialogsPresenterComponent,
    modifier: Modifier = Modifier,
) {
    val sliderValue by component.sliderValue.subscribeAsState()

    DialogsPresenterPane(
        sliderValue = sliderValue,
        onShowToastClicked = component::onShowToast,
        onShowInfoDialogClicked = component::onShowInfoDialog,
        onShowConfirmationDialogClicked = component::onShowConfirmationDialog,
        onShowSliderClicked = component::onShowSliderDialog,
        modifier = modifier,
    )
}

@Composable
fun DialogsPresenterPane(
    sliderValue: Int,
    onShowToastClicked: () -> Unit,
    onShowInfoDialogClicked: () -> Unit,
    onShowConfirmationDialogClicked: () -> Unit,
    onShowSliderClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        ShowDialogButton(
            title = stringResource(Res.string.dialogs_presenter_show_toast_title),
            onShowDialog = onShowToastClicked,
        )
        Spacer(16.dp)
        ShowDialogButton(
            title = stringResource(Res.string.dialogs_presenter_show_info_title),
            onShowDialog = onShowInfoDialogClicked,
        )
        Spacer(16.dp)
        ShowDialogButton(
            title = stringResource(Res.string.dialogs_presenter_show_confirmation_title),
            onShowDialog = onShowConfirmationDialogClicked,
        )
        Spacer(16.dp)
        Text(
            text = stringResource(
                Res.string.dialogs_presenter_slider_selected_value,
                stringResource(Res.string.common_percent, sliderValue.toUInt())
            )
        )
        Spacer(8.dp)
        ShowDialogButton(
            title = stringResource(Res.string.dialogs_presenter_show_slider_title),
            onShowDialog = onShowSliderClicked,
        )
    }
}

@Composable
private fun ShowDialogButton(
    title: String,
    onShowDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onShowDialog,
        modifier = modifier.widthIn(min = 150.dp).heightIn(min = 75.dp),
    ) {
        Text(
            text = title,
            maxLines = 1,
        )
    }
}
package com.example.dialogs.presentation.dialogs.info

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InfoDialogPane(
    component: InfoDialogComponent,
    modifier: Modifier = Modifier,
) {
    InfoDialogPane(
        title = component.infoDialog.title,
        message = component.infoDialog.message,
        buttonTitle = component.infoDialog.buttonTitle,
        onDismissClicked = component::onDismissClicked,
        modifier = modifier,
    )
}

@Composable
fun InfoDialogPane(
    title: String,
    message: String,
    buttonTitle: String,
    onDismissClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = onDismissClicked,
            ) {
                Text(buttonTitle)
            }
        },
        onDismissRequest = onDismissClicked,
        modifier = modifier,
    )
}
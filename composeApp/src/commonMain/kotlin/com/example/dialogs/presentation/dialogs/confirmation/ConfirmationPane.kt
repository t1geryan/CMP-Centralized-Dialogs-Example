package com.example.dialogs.presentation.dialogs.confirmation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback

@Composable
fun ConfirmationPane(
    component: ConfirmationComponent,
    modifier: Modifier = Modifier,
) {
    ConfirmationPane(
        confirmationDialog = component.confirmationDialog,
        onDismissClicked = component.onDismiss,
        modifier = modifier,
    )
}

@Composable
fun ConfirmationPane(
    confirmationDialog: DialogModel.ConfirmationDialog,
    onDismissClicked: (DismissCallback) -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        title = {
            Text(confirmationDialog.title)
        },
        text = {
            Text(confirmationDialog.message)
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismissClicked(confirmationDialog.onConfirm)
                },
            ) {
                Text(confirmationDialog.confirmTitle)
            }
        },
        dismissButton = confirmationDialog.cancelTitle?.let { title: String ->
            {
                Button(
                    onClick = {
                        onDismissClicked(confirmationDialog.onCancel)
                    }
                ) {
                    Text(title)
                }
            }
        },
        onDismissRequest = {
            onDismissClicked(confirmationDialog.onDismiss)
        },
        modifier = modifier,
    )
}
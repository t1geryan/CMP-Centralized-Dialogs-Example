package com.example.dialogs.presentation.dialogs.toast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import com.example.dialogs.presentation.dialogs.DialogModel

@Composable
fun ToastPane(
    component: ToastComponent,
    modifier: Modifier = Modifier,
) {
    ToastPane(
        toast = component.toast,
        modifier = modifier,
    )
}

@Composable
fun ToastPane(
    toast: DialogModel.Toast,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Snackbar(modifier = Modifier.align(BiasAlignment(0.0f, 0.9f)).fillMaxWidth(0.75f)) {
            Text(toast.message)
        }
    }
}
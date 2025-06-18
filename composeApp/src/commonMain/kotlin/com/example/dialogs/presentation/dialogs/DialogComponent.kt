package com.example.dialogs.presentation.dialogs

typealias DismissCallback = () -> Unit

interface DialogComponent {

    val onDismiss: (DismissCallback) -> Unit
}
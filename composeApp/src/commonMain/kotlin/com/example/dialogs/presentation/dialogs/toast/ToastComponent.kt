package com.example.dialogs.presentation.dialogs.toast

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback

interface ToastComponent : DialogComponent {
    val toast: DialogModel.Toast
}

class DefaultToastComponent(
    componentContext: ComponentContext,
    override val toast: DialogModel.Toast,
    override val onDismiss: (DismissCallback) -> Unit,
) : ToastComponent, ComponentContext by componentContext
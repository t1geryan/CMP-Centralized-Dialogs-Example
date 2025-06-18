package com.example.dialogs.presentation.dialogs.toast

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel

interface ToastComponent : DialogComponent {
    val toast: DialogModel.Toast
}

class DefaultToastComponent(
    componentContext: ComponentContext,
    override val toast: DialogModel.Toast,
) : ToastComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        // Toast can't dismiss itself
    }
}
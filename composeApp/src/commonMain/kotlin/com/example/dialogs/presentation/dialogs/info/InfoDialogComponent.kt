package com.example.dialogs.presentation.dialogs.info

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel

interface InfoDialogComponent : DialogComponent {
    val infoDialog: DialogModel.InfoDialog
}

class DefaultInfoDialogComponent(
    componentContext: ComponentContext,
    private val onDismiss: () -> Unit,
    override val infoDialog: DialogModel.InfoDialog,
) : InfoDialogComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        onDismiss()
    }
}
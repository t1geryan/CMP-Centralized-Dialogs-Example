package com.example.dialogs.presentation.dialogs.info

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback

interface InfoDialogComponent : DialogComponent {
    val infoDialog: DialogModel.InfoDialog
}

class DefaultInfoDialogComponent(
    componentContext: ComponentContext,
    override val onDismiss: (DismissCallback) -> Unit,
    override val infoDialog: DialogModel.InfoDialog,
) : InfoDialogComponent, ComponentContext by componentContext
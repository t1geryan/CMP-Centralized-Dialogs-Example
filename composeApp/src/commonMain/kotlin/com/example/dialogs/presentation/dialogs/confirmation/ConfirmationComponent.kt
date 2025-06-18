package com.example.dialogs.presentation.dialogs.confirmation

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback

interface ConfirmationComponent : DialogComponent {
    val confirmationDialog: DialogModel.ConfirmationDialog
}

class DefaultConfirmationComponent(
    componentContext: ComponentContext,
    override val confirmationDialog: DialogModel.ConfirmationDialog,
    override val onDismiss: (DismissCallback) -> Unit,
) : ConfirmationComponent, ComponentContext by componentContext
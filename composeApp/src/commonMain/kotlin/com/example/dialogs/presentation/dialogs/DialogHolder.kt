package com.example.dialogs.presentation.dialogs

import com.arkivanov.decompose.value.Value

interface DialogHolder {
    val isDialogOpen: Value<Boolean>

    fun showDialog(model: DialogModel)
}
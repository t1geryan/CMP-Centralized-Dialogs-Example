package com.example.dialogs.presentation.dialogs

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value

interface DialogHolder {
    val dialog: Value<ChildSlot<*, DialogComponent>>

    fun showDialog(model: DialogModel)
}
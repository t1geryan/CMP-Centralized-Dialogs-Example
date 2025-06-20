package com.example.dialogs.presentation.dialogs

import com.arkivanov.decompose.value.Value

interface DialogHolder {
    val isDialogOpen: Value<Boolean>

    /**
     * **It is strongly recommended to call this method on the Main thread.**
     */
    fun showDialog(model: DialogModel)
}
package com.example.dialogs.presentation.dialogs.toast

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnResume
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface ToastComponent : DialogComponent {
    val toast: DialogModel.Toast
}

class DefaultToastComponent(
    componentContext: ComponentContext,
    override val toast: DialogModel.Toast,
    override val onDismiss: (DismissCallback) -> Unit,
) : ToastComponent, ComponentContext by componentContext {
    private val scope = coroutineScope(Job() + Dispatchers.Main.immediate)

    init {
        lifecycle.doOnResume {
            scope.launch {
                delay(toast.duration)
                onDismiss(toast.onDismiss)
            }
        }
    }
}
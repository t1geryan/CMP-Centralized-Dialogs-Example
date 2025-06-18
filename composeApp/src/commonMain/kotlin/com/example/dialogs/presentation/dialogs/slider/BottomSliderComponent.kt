package com.example.dialogs.presentation.dialogs.slider

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback

interface BottomSliderComponent : DialogComponent {
    val bottomSliderDialog: DialogModel.BottomSliderDialog

    val value: Value<Float>

    fun onDrag(newValue: Float)
}

class DefaultBottomSliderComponent(
    componentContext: ComponentContext,
    override val onDismiss: (DismissCallback) -> Unit,
    override val bottomSliderDialog: DialogModel.BottomSliderDialog,
) : BottomSliderComponent, ComponentContext by componentContext {

    private val _value: MutableValue<Float> = MutableValue(bottomSliderDialog.initialValue)
    override val value: Value<Float>
        get() = _value

    override fun onDrag(newValue: Float) {
        _value.update { newValue }
    }
}
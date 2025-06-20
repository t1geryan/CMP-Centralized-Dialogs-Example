package com.example.dialogs.presentation.features.dialogs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.dialogs.common.consts.Consts
import com.example.dialogs.presentation.dialogs.DialogHolder
import com.example.dialogs.presentation.dialogs.DialogModel
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.common_percent
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_cancel_button_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_confirm_button_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_message
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_result_canceled
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_result_confirmed
import dialogsexample.composeapp.generated.resources.dialogs_presenter_confirmation_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_info_button_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_info_message
import dialogsexample.composeapp.generated.resources.dialogs_presenter_info_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_slider_selected_value
import dialogsexample.composeapp.generated.resources.dialogs_presenter_slider_title
import dialogsexample.composeapp.generated.resources.dialogs_presenter_toast_message
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

interface DialogsPresenterComponent {
    val sliderValue: Value<Int>

    val isDialogOpen: Value<Boolean>

    fun onShowToast()

    fun onShowInfoDialog()

    fun onShowConfirmationDialog()

    fun onShowSliderDialog()
}

class DefaultDialogsPresenterComponent(
    componentContext: ComponentContext,
    private val dialogHolder: DialogHolder,
) : DialogsPresenterComponent, ComponentContext by componentContext {
    private val scope = coroutineScope()

    private val _sliderValue = MutableValue(0)
    override val sliderValue: Value<Int>
        get() = _sliderValue

    override val isDialogOpen: Value<Boolean> = dialogHolder.isDialogOpen

    override fun onShowToast() {
        scope.launch {
            dialogHolder.showDialog(
                DialogModel.Toast(
                    message = getString(Res.string.dialogs_presenter_toast_message)
                )
            )
        }
    }

    override fun onShowInfoDialog() {
        scope.launch {
            dialogHolder.showDialog(
                DialogModel.InfoDialog(
                    title = getString(Res.string.dialogs_presenter_info_title),
                    message = getString(Res.string.dialogs_presenter_info_message),
                    buttonTitle = getString(Res.string.dialogs_presenter_info_button_title),
                )
            )
        }
    }

    override fun onShowConfirmationDialog() {
        scope.launch {
            dialogHolder.showDialog(
                DialogModel.ConfirmationDialog(
                    title = getString(Res.string.dialogs_presenter_confirmation_title),
                    message = getString(Res.string.dialogs_presenter_confirmation_message),
                    confirmTitle = getString(Res.string.dialogs_presenter_confirmation_confirm_button_title),
                    cancelTitle = getString(Res.string.dialogs_presenter_confirmation_cancel_button_title),
                    onConfirm = {
                        scope.launch {
                            dialogHolder.showDialog(
                                DialogModel.Toast(
                                    message = getString(Res.string.dialogs_presenter_confirmation_result_confirmed),
                                )
                            )
                        }
                    },
                    onCancel = {
                        scope.launch {
                            dialogHolder.showDialog(
                                DialogModel.Toast(
                                    message = getString(Res.string.dialogs_presenter_confirmation_result_canceled),
                                )
                            )
                        }
                    }
                )
            )
        }
    }

    override fun onShowSliderDialog() {
        scope.launch {
            dialogHolder.showDialog(
                DialogModel.BottomSliderDialog(
                    titleFormatter = {
                        getString(Res.string.dialogs_presenter_slider_title)
                    },
                    valueFormatter = { value ->
                        getString(
                            Res.string.dialogs_presenter_slider_selected_value,
                            getString(Res.string.common_percent, value.toUInt())
                        )
                    },
                    maxValue = Consts.Percent.MAX.toFloat(),
                    minValue = Consts.Percent.MIN.toFloat(),
                    initialValue = _sliderValue.value.toFloat(),
                    onValueSelected = { newValue ->
                        _sliderValue.update { newValue.toInt() }
                    },
                    stepsCount = Consts.Percent.MAX - Consts.Percent.MIN - 1U,
                )
            )
        }
    }
}
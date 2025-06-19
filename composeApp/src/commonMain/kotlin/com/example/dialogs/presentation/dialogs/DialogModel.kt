package com.example.dialogs.presentation.dialogs

import androidx.compose.runtime.Immutable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Immutable
sealed interface DialogModel {
    val onDismiss: () -> Unit
    val isLocal: Boolean

    @Immutable
    class Toast(
        val message: String,
        val duration: Duration = 3.seconds,
        override val onDismiss: () -> Unit = {},
        override val isLocal: Boolean = true,
    ) : DialogModel

    @Immutable
    class InfoDialog(
        val title: String,
        val message: String,
        val buttonTitle: String,
        override val onDismiss: () -> Unit = {},
        override val isLocal: Boolean = true,
    ) : DialogModel

    @Immutable
    class ConfirmationDialog(
        val title: String,
        val message: String,
        val onConfirm: () -> Unit,
        val confirmTitle: String,
        val onCancel: () -> Unit = {},
        val cancelTitle: String?,
        override val onDismiss: () -> Unit = {},
        override val isLocal: Boolean = true,
    ) : DialogModel

    @Immutable
    class BottomSliderDialog(
        val titleFormatter: (Float) -> String,
        val valueFormatter: (Float) -> String,
        val maxValue: Float,
        val minValue: Float,
        val initialValue: Float,
        val onValueSelected: (Float) -> Unit,
        val stepsCount: Int,
        override val onDismiss: () -> Unit = {},
        override val isLocal: Boolean = true,
    ) : DialogModel
}
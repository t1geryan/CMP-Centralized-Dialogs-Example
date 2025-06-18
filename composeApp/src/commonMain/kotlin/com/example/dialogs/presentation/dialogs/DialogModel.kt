package com.example.dialogs.presentation.dialogs

import androidx.compose.runtime.Immutable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Immutable
sealed interface DialogModel {
    val onDismiss: () -> Unit

    @Immutable
    class Toast(
        val message: String,
        val duration: Duration = 3.seconds,
        override val onDismiss: () -> Unit = {},
    ) : DialogModel

    @Immutable
    class InfoDialog(
        val title: String,
        val message: String,
        val buttonTitle: String,
        override val onDismiss: () -> Unit = {},
    ) : DialogModel

    @Immutable
    class ConfirmationDialog(
        val title: String,
        val message: String,
        val onConfirm: () -> Unit,
        val onCancel: () -> Unit = {},
        override val onDismiss: () -> Unit = {},
    ) : DialogModel

    @Immutable
    class BottomSliderDialog(
        val title: String,
        val message: String,
        override val onDismiss: () -> Unit = {}
    ) : DialogModel
}
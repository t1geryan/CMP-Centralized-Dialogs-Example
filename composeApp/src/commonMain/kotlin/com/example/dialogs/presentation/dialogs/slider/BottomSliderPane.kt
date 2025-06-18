package com.example.dialogs.presentation.dialogs.slider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback
import com.example.dialogs.presentation.widgets.spacing.Spacer

@Composable
fun BottomSliderPane(
    component: BottomSliderComponent,
    modifier: Modifier = Modifier,
) {
    val value by component.value.subscribeAsState()

    BottomSliderPane(
        value = value,
        onValueChanged = component::onDrag,
        onDismiss = component.onDismiss,
        bottomSliderDialog = component.bottomSliderDialog,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSliderPane(
    value: Float,
    onValueChanged: (Float) -> Unit,
    onDismiss: (DismissCallback) -> Unit,
    bottomSliderDialog: DialogModel.BottomSliderDialog,
    modifier: Modifier = Modifier,
) {
    val colors = SliderDefaults.colors(
        activeTickColor = Color.Transparent,
        inactiveTickColor = Color.Transparent
    )

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss {
                bottomSliderDialog.onValueSelected(value)
            }
        },
        modifier = modifier,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = {
                    onDismiss(bottomSliderDialog.onDismiss)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = null,
                )
            }
        }
        Spacer(8.dp)
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = bottomSliderDialog.titleFormatter(value),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(16.dp)
        Slider(
            value = value,
            onValueChange = onValueChanged,
            steps = bottomSliderDialog.stepsCount,
            colors = colors,
            valueRange = bottomSliderDialog.minValue..bottomSliderDialog.maxValue,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )
        Spacer(8.dp)
        Text(text = bottomSliderDialog.valueFormatter(value))
    }
}
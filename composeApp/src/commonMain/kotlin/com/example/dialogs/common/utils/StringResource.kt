package com.example.dialogs.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

@Composable
fun getStringAsync(
    key: Any,
    default: () -> String = { "" },
    block: suspend () -> String,
): String {
    val scope = rememberCoroutineScope()
    val str by remember(key) {
        val mutableState = mutableStateOf(default())
        scope.launch(start = CoroutineStart.UNDISPATCHED) {
            mutableState.value = block()
        }
        mutableState
    }
    return str
}
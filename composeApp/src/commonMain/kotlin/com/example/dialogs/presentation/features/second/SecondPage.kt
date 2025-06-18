package com.example.dialogs.presentation.features.second

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dialogsexample.composeapp.generated.resources.Res
import dialogsexample.composeapp.generated.resources.second_page_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SecondPage(
    component: SecondComponent,
    modifier: Modifier = Modifier,
) {
    SecondPage(modifier = modifier)
}

@Composable
fun SecondPage(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(Res.string.second_page_title),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
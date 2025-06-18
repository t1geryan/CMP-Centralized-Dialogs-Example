package com.example.dialogs.presentation.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainPage(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    MainPage(modifier = modifier)
}

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier)
}
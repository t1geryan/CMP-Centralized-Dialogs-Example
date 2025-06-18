package com.example.dialogs.presentation.features.first

import com.arkivanov.decompose.ComponentContext

interface FirstComponent {
    fun onNextClicked()
}

class DefaultFirstComponent(
    componentContext: ComponentContext,
    private val onNavigateForward: () -> Unit,
) : FirstComponent, ComponentContext by componentContext {

    override fun onNextClicked() {
        onNavigateForward()
    }
}
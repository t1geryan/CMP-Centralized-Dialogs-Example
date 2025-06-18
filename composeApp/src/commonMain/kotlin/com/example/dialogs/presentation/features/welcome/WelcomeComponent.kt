package com.example.dialogs.presentation.features.welcome

import com.arkivanov.decompose.ComponentContext

interface WelcomeComponent {
    fun onStartClicked()
}

class DefaultWelcomeComponent(
    componentContext: ComponentContext,
    private val onNavigateForward: () -> Unit,
) : WelcomeComponent, ComponentContext by componentContext {

    override fun onStartClicked() {
        onNavigateForward()
    }
}
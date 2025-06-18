package com.example.dialogs.presentation.features.login

import com.arkivanov.decompose.ComponentContext
import com.example.dialogs.presentation.base.NavigateBackComponent

interface LoginComponent : NavigateBackComponent {

    fun onLoginClicked()
}

class DefaultLoginComponent(
    componentContext: ComponentContext,
    override val onNavigateBack: () -> Unit,
    private val onNavigateForward: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {

    override fun onLoginClicked() {
        onNavigateForward()
    }
}

package com.example.dialogs.presentation.features.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.example.dialogs.presentation.base.NavigateBackComponent
import com.example.dialogs.presentation.features.dialogs.DefaultDialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface LoginComponent : NavigateBackComponent {

    val dialogsPresenter: DialogsPresenterComponent

    fun onLoginClicked()
}

class DefaultLoginComponent(
    componentContext: ComponentContext,
    override val onNavigateBack: () -> Unit,
    private val onNavigateForward: () -> Unit,
) : LoginComponent, ComponentContext by componentContext, KoinComponent {

    override val dialogsPresenter: DialogsPresenterComponent = DefaultDialogsPresenterComponent(
        componentContext = childContext(key = "LoginDialogPresenter"),
        dialogHolder = get(),
    )

    override fun onLoginClicked() {
        onNavigateForward()
    }
}

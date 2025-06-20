package com.example.dialogs.presentation.features.third

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.example.dialogs.presentation.base.NavigateBackComponent
import com.example.dialogs.presentation.features.dialogs.DefaultDialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface ThirdComponent : NavigateBackComponent {
    val dialogsPresenter: DialogsPresenterComponent
}

class DefaultThirdComponent(
    componentContext: ComponentContext,
    override val onNavigateBack: () -> Unit,
) : ThirdComponent, ComponentContext by componentContext, KoinComponent {

    override val dialogsPresenter: DialogsPresenterComponent = DefaultDialogsPresenterComponent(
        componentContext = childContext(key = "ThirdScreenDialogPresenter"),
        dialogHolder = get(),
    )
}
package com.example.dialogs.presentation.features.second

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.example.dialogs.presentation.features.dialogs.DefaultDialogsPresenterComponent
import com.example.dialogs.presentation.features.dialogs.DialogsPresenterComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface SecondComponent {
    val dialogsPresenter: DialogsPresenterComponent

    fun onNavigateNextClicked()
}

class DefaultSecondComponent(
    componentContext: ComponentContext,
    private val onNavigateToThird: () -> Unit,
) : SecondComponent, ComponentContext by componentContext, KoinComponent {

    override val dialogsPresenter: DialogsPresenterComponent = DefaultDialogsPresenterComponent(
        componentContext = childContext(key = "SecondScreenDialogPresenter"),
        dialogHolder = get(),
    )

    override fun onNavigateNextClicked() {
        onNavigateToThird()
    }
}
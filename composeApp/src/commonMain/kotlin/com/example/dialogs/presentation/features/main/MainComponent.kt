package com.example.dialogs.presentation.features.main

import com.arkivanov.decompose.ComponentContext

interface MainComponent {
    // val stack: Value<ChildStack<*, Child>>

    sealed interface Child
}

class DefaultMainComponent(
    componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext

package com.example.dialogs.presentation.features.root

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

interface RootComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val onMinimize: () -> Unit = {},
) : RootComponent, ComponentContext by componentContext, KoinComponent

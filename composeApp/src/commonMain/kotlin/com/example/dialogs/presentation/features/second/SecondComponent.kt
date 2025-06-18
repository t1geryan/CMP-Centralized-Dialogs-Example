package com.example.dialogs.presentation.features.second

import com.arkivanov.decompose.ComponentContext

interface SecondComponent

class DefaultSecondComponent(
    componentContext: ComponentContext,
) : SecondComponent, ComponentContext by componentContext
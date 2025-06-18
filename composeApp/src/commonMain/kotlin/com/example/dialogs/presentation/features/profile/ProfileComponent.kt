package com.example.dialogs.presentation.features.profile

import com.arkivanov.decompose.ComponentContext

interface ProfileComponent

class DefaultProfileComponent(
    componentContext: ComponentContext,
) : ProfileComponent, ComponentContext by componentContext
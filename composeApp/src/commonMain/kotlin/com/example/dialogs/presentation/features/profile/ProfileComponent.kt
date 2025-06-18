package com.example.dialogs.presentation.features.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.dialogs.presentation.features.first.DefaultFirstComponent
import com.example.dialogs.presentation.features.first.FirstComponent
import com.example.dialogs.presentation.features.second.DefaultSecondComponent
import com.example.dialogs.presentation.features.second.SecondComponent
import kotlinx.serialization.Serializable

interface ProfileComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class First(val component: FirstComponent) : Child
        class Second(val component: SecondComponent) : Child
    }
}

class DefaultProfileComponent(
    componentContext: ComponentContext,
) : ProfileComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, ProfileComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.First,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(config: Config, componentContext: ComponentContext) =
        when (config) {
            Config.First -> ProfileComponent.Child.First(createFirstChild(componentContext))
            Config.Second -> ProfileComponent.Child.Second(createSecondChild(componentContext))
        }

    private fun createFirstChild(componentContext: ComponentContext) = DefaultFirstComponent(
        componentContext = componentContext,
        onNavigateForward = { navigation.pushNew(Config.Second) },
    )

    private fun createSecondChild(componentContext: ComponentContext) = DefaultSecondComponent(
        componentContext = componentContext
    )

    @Serializable
    sealed interface Config {
        @Serializable
        data object First : Config

        @Serializable
        data object Second : Config
    }
}
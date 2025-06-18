package com.example.dialogs.presentation.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.dialogs.presentation.features.login.DefaultLoginComponent
import com.example.dialogs.presentation.features.login.LoginComponent
import com.example.dialogs.presentation.features.main.DefaultMainComponent
import com.example.dialogs.presentation.features.main.MainComponent
import com.example.dialogs.presentation.features.welcome.DefaultWelcomeComponent
import com.example.dialogs.presentation.features.welcome.WelcomeComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Welcome(val component: WelcomeComponent) : Child
        class Login(val component: LoginComponent) : Child
        class Main(val component: MainComponent) : Child
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val onMinimize: () -> Unit = {},
) : RootComponent, ComponentContext by componentContext, KoinComponent {
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Welcome,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child =
        when (config) {
            Config.Welcome -> RootComponent.Child.Welcome(createWelcomeChild(componentContext))
            Config.Login -> RootComponent.Child.Login(createLoginChild(componentContext))
            Config.Main -> RootComponent.Child.Main(createMainChild(componentContext))
        }

    private fun createWelcomeChild(componentContext: ComponentContext): WelcomeComponent =
        DefaultWelcomeComponent(
            componentContext = componentContext,
            onNavigateForward = {
                navigation.pushNew(Config.Login)
            },
        )

    private fun createLoginChild(componentContext: ComponentContext): LoginComponent =
        DefaultLoginComponent(
            componentContext = componentContext,
            onNavigateBack = {
                navigation.pop()
            },
            onNavigateForward = {
                // navigation.pushNew(Config.)
            }
        )

    private fun createMainChild(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
        )

    @Serializable // kotlinx-serialization plugin must be applied
    private sealed interface Config {
        @Serializable
        data object Welcome : Config

        @Serializable
        data object Login : Config

        @Serializable
        data object Main : Config
    }
}

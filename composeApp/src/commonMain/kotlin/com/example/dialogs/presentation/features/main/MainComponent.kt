package com.example.dialogs.presentation.features.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.example.dialogs.presentation.features.profile.DefaultProfileComponent
import com.example.dialogs.presentation.features.profile.ProfileComponent
import com.example.dialogs.presentation.features.settings.DefaultSettingsComponent
import com.example.dialogs.presentation.features.settings.SettingsComponent
import kotlinx.serialization.Serializable

interface MainComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onTabSelected(tab: MainTab)

    sealed interface Child {
        val component: Any

        class Profile(override val component: ProfileComponent) : Child
        class Settings(override val component: SettingsComponent) : Child
    }
}

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val onNavigateBack: () -> Unit,
) : MainComponent, ComponentContext by componentContext {

    private val backCallback = BackCallback {
        handleBackNavigation()
    }

    init {
        backHandler.register(backCallback)
    }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, MainComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.INITIAL,
        handleBackButton = false,
        childFactory = ::createChild,
        serializer = Config.serializer(),
    )

    override fun onTabSelected(tab: MainTab) {
        val configToNavigate = Config.getByTab(tab)
        val currentConfig = stack.active.configuration as Config

        if (configToNavigate != currentConfig) {
            navigation.bringToFront(configToNavigate)
        }
    }

    private fun handleBackNavigation() {
        val currentConfig = stack.active.configuration as Config
        if (currentConfig == Config.INITIAL) {
            onNavigateBack()
        } else {
            navigation.pop()
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext,
    ): MainComponent.Child =
        when (config) {
            Config.Profile -> MainComponent.Child.Profile(createProfileChild(componentContext))
            Config.Settings -> MainComponent.Child.Settings(createSettingsChild(componentContext))
        }

    private fun createProfileChild(componentContext: ComponentContext): ProfileComponent {
        return DefaultProfileComponent(componentContext = componentContext)
    }

    private fun createSettingsChild(componentContext: ComponentContext): SettingsComponent {
        return DefaultSettingsComponent(componentContext = componentContext)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Profile : Config

        @Serializable
        data object Settings : Config

        companion object {
            fun getByTab(tab: MainTab): Config = when (tab) {
                MainTab.PROFILE -> Profile
                MainTab.SETTINGS -> Settings
            }

            val INITIAL = Profile
        }
    }
}

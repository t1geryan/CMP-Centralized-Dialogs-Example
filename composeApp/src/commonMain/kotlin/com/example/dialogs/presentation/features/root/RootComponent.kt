package com.example.dialogs.presentation.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.child
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogHolder
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.confirmation.DefaultConfirmationComponent
import com.example.dialogs.presentation.dialogs.info.DefaultInfoDialogComponent
import com.example.dialogs.presentation.dialogs.slider.DefaultBottomSliderComponent
import com.example.dialogs.presentation.dialogs.toast.DefaultToastComponent
import com.example.dialogs.presentation.features.login.DefaultLoginComponent
import com.example.dialogs.presentation.features.login.LoginComponent
import com.example.dialogs.presentation.features.main.DefaultMainComponent
import com.example.dialogs.presentation.features.main.MainComponent
import com.example.dialogs.presentation.features.welcome.DefaultWelcomeComponent
import com.example.dialogs.presentation.features.welcome.WelcomeComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

interface RootComponent : DialogHolder {
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
    private val navigationScope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Welcome,
            handleBackButton = true,
            childFactory = ::createChild
        )


    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val dialog: Value<ChildSlot<*, DialogComponent>> =
        childSlot(
            source = dialogNavigation,
            handleBackButton = true,
            serializer = null,
            childFactory = ::createDialog,
        )

    override fun showDialog(model: DialogModel) {
        if (dialog.child != null) return

        when (model) {
            is DialogModel.Toast -> showToast(model)
            is DialogModel.InfoDialog -> showInfoDialog(model)
            is DialogModel.ConfirmationDialog -> showConfirmationDialog(model)
            is DialogModel.BottomSliderDialog -> showBottomSliderDialog(model)
        }
    }

    private fun showToast(model: DialogModel.Toast) {
        navigationScope.launch {
            dialogNavigation.activate(DialogConfig.Toast(toast = model))
            delay(model.duration)
            dialogNavigation.dismiss { isSuccess ->
                if (isSuccess) model.onDismiss()
            }
        }
    }

    private fun showInfoDialog(model: DialogModel.InfoDialog) {
        dialogNavigation.activate(
            DialogConfig.InfoDialog(infoDialog = model)
        )
    }

    private fun showConfirmationDialog(model: DialogModel.ConfirmationDialog) {
        dialogNavigation.activate(
            DialogConfig.ConfirmationDialog(confirmationDialog = model)
        )
    }

    private fun showBottomSliderDialog(model: DialogModel.BottomSliderDialog) {
        dialogNavigation.activate(
            DialogConfig.BottomSlider(bottomSlider = model)
        )
    }

    private fun createDialog(
        config: DialogConfig,
        componentContext: ComponentContext,
    ): DialogComponent =
        when (config) {
            is DialogConfig.InfoDialog -> DefaultInfoDialogComponent(
                componentContext = componentContext,
                onDismiss = { onComplete ->
                    dialogNavigation.dismiss { isSuccess ->
                        if (isSuccess) onComplete()
                    }
                },
                infoDialog = config.infoDialog,
            )

            is DialogConfig.Toast -> DefaultToastComponent(
                componentContext = componentContext,
                toast = config.toast,
                // handled automatically on show
                onDismiss = {},
            )

            is DialogConfig.ConfirmationDialog -> DefaultConfirmationComponent(
                componentContext = componentContext,
                onDismiss = { onComplete ->
                    dialogNavigation.dismiss { isSuccess ->
                        if (isSuccess) onComplete()
                    }
                },
                confirmationDialog = config.confirmationDialog,
            )

            // TODO Tty to extract onDismiss to separate function cause they are same
            is DialogConfig.BottomSlider -> DefaultBottomSliderComponent(
                componentContext = componentContext,
                onDismiss = { onComplete ->
                    dialogNavigation.dismiss { isSuccess ->
                        if (isSuccess) onComplete()
                    }
                },
                bottomSliderDialog = config.bottomSlider,
            )
        }

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
                navigation.replaceAll(Config.Main)
            },
            onShowDialog = ::showDialog,
        )

    private fun createMainChild(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            onNavigateBack = onMinimize,
            onShowDialog = ::showDialog,
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

    private sealed interface DialogConfig {
        class Toast(val toast: DialogModel.Toast) : DialogConfig

        class InfoDialog(val infoDialog: DialogModel.InfoDialog) : DialogConfig

        class ConfirmationDialog(
            val confirmationDialog: DialogModel.ConfirmationDialog
        ) : DialogConfig

        class BottomSlider(val bottomSlider: DialogModel.BottomSliderDialog) : DialogConfig
    }
}

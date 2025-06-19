package com.example.dialogs.presentation.features.root

import com.arkivanov.decompose.Cancellation
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
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.example.dialogs.presentation.contracts.NavigationChild
import com.example.dialogs.presentation.contracts.StackNavigationComponent
import com.example.dialogs.presentation.contracts.findAllStackNavigationSubcomponents
import com.example.dialogs.presentation.dialogs.DialogComponent
import com.example.dialogs.presentation.dialogs.DialogHolder
import com.example.dialogs.presentation.dialogs.DialogModel
import com.example.dialogs.presentation.dialogs.DismissCallback
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
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

interface RootComponent : DialogHolder, StackNavigationComponent<RootComponent.Child> {

    sealed interface Child : NavigationChild {
        class Welcome(override val component: WelcomeComponent) : Child
        class Login(override val component: LoginComponent) : Child
        class Main(override val component: MainComponent) : Child
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

        val dialogConfig = when (model) {
            is DialogModel.Toast -> DialogConfig.Toast(model)
            is DialogModel.InfoDialog -> DialogConfig.InfoDialog(model)
            is DialogModel.ConfirmationDialog -> DialogConfig.ConfirmationDialog(model)
            is DialogModel.BottomSliderDialog -> DialogConfig.BottomSlider(model)
        }
        dialogNavigation.activate(dialogConfig)
    }

    private val dialogHolderModule = module {
        single<DialogHolder> {
            this@DefaultRootComponent
        }
    }

    init {
        lifecycle.doOnCreate {
            getKoin().loadModules(listOf(dialogHolderModule))
        }
        lifecycle.doOnDestroy {
            getKoin().unloadModules(listOf(dialogHolderModule))
        }
    }

    private var subscriptions = emptyList<Cancellation>()

    init {
        lifecycle.doOnCreate {
            subscribeOnWholeNavigation()
        }
        lifecycle.doOnDestroy {
            cancelNavigationSubscription()
        }
    }

    private fun subscribeOnWholeNavigation() {
        subscriptions = findAllStackNavigationSubcomponents().map {
            var skippedFirst = false
            it.childStack.subscribe {
                if (!skippedFirst) {
                    skippedFirst = true
                    return@subscribe
                }
                checkDialogToClose()
                cancelNavigationSubscription()
                subscribeOnWholeNavigation()
            }
        }
    }

    private var isDialogCheckingInProcess = false
    private fun checkDialogToClose() {
        if (isDialogCheckingInProcess) return
        val dialogModel = (dialog.child?.configuration as? DialogConfig)?.model
        if (dialogModel != null && dialogModel.isLocal) {
            isDialogCheckingInProcess = true

            dialogNavigation.dismiss { isSuccess ->
                if (isSuccess) dialogModel.onDismiss.invoke()
                isDialogCheckingInProcess = false
            }
        }
    }

    private fun cancelNavigationSubscription() {
        subscriptions.forEach { it.cancel() }
        subscriptions = emptyList()
    }

    private fun createDialog(
        config: DialogConfig,
        componentContext: ComponentContext,
    ): DialogComponent =
        when (config) {
            is DialogConfig.InfoDialog -> DefaultInfoDialogComponent(
                componentContext = componentContext,
                onDismiss = ::onDismissDialog,
                infoDialog = config.model,
            )

            is DialogConfig.Toast -> DefaultToastComponent(
                componentContext = componentContext,
                onDismiss = ::onDismissDialog,
                toast = config.model,
            )

            is DialogConfig.ConfirmationDialog -> DefaultConfirmationComponent(
                componentContext = componentContext,
                onDismiss = ::onDismissDialog,
                confirmationDialog = config.model,
            )

            is DialogConfig.BottomSlider -> DefaultBottomSliderComponent(
                componentContext = componentContext,
                onDismiss = ::onDismissDialog,
                bottomSliderDialog = config.model,
            )
        }

    private fun onDismissDialog(onComplete: DismissCallback) {
        dialogNavigation.dismiss { isSuccess ->
            if (isSuccess) onComplete()
        }
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
        val model: DialogModel

        class Toast(override val model: DialogModel.Toast) : DialogConfig

        class InfoDialog(override val model: DialogModel.InfoDialog) : DialogConfig

        class ConfirmationDialog(override val model: DialogModel.ConfirmationDialog) : DialogConfig

        class BottomSlider(override val model: DialogModel.BottomSliderDialog) : DialogConfig
    }
}

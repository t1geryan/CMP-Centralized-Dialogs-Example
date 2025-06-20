# CMP Centralized Dialogs Example

A Compose Multiplatform application demonstrating centralized dialog management across Android, iOS, and Desktop platforms using the Decompose navigation library.

## Overview

This project showcases a component-based architecture with a centralized dialog system that allows any part of the application to display dialogs (e.g. Toast, Info, Confirmation, Bottom Sheet Slider) through a unified interface DialogHolder. This interface can be provided to the component (e.g. Screen, Group of Screens, Separate Widget) using DI, Composition Local Provider (from Compose) and manually using callbacks.

## Project Features

- **Multiplatform Support**: Runs on Android, iOS, and Desktop
- **Centralized Dialog Management**: All dialogs managed through a single `DialogHolder` interface on every screen of the App.
- **Component-Based Navigation**: Uses Decompose library for type-safe navigation
- **Multiple Dialog Types**: Toast, Info Dialog, Confirmation Dialog, and Bottom Slider
- **Automatic Dialog Lifecycle**: Local dialogs automatically dismiss on navigation changes

## Getting Started

### Prerequisites
- Android Studio
- Kotlin Multiplatform Mobile plugin
- JDK 21

### Running the Application

**Desktop:**
```bash
./gradlew :composeApp:run
```
Also you can create Gradle configuration and input `:composeApp:run` in "Run" field

**Android:**
Open in Android Studio and run on device/emulator using Anroid App configuration. Select composeApp as module to run.

**iOS:**
Open the iOS project in Xcode and run or use iOS Application configuration in Android Studio

## Architecture

### Nested Navigation System
- `RootComponent`: Main navigation hub managing application-level stack routing
- `MainComponent`: Tab-based navigation
- `ProfileComponent`: Stack navigation for one of tabs

#### Navigation Flow
1. Root: Welcome Screen → Login Screen → Main Screen
2. Main Screen: Profile and Settings tabs
3. Profile tab: First Screen -> Second Screen -(root)-> Third Screen
   
### Dialog Management
- Centralized through `DialogHolder` interface implemented by `RootComponent`
- Four example dialog types: Toast, Info, Confirmation, and Bottom Slider
- Automatic cleanup on navigation changes

### UI Presentation
- Compose-based UI with shared design system
- Localized string resources

## Project Structure

```
composeApp/
├── src/
│   ├── androidMain/           # Android-specific code
│   ├── commonMain/            # Shared multiplatform code
│   │   ├── composeResources/  # Shared resources
│   │   └── kotlin/
│   │       └── com/example/dialogs/
│   │           ├── di/        # Dependency injection
│   │           └── presentation/
│   │               ├── dialogs/    # Dialog contracts models and components
│   │               └── features/   # Feature screens
│   │               └── contracts/  # UI-related contracts
│   │               └── widgets/    # Stateless reusable widgets
│   │               └── theme/      # App theming
│   ├── desktopMain/          # Desktop-specific code
│   └── iosMain/              # iOS-specific code
```

## Usage

### Passing DialogHolder to Screens

The `DialogHolder` can be passed to the screens through dependency injection using Koin. The `RootComponent` implements `DialogHolder` and registers itself as a singleton.
Components receive the `DialogHolder` through constructor injection:
```kotlin
class DefaultDialogsPresenterComponent(
    componentContext: ComponentContext,
    private val dialogHolder: DialogHolder,
) : DialogsPresenterComponent, ComponentContext by componentContext
```
So parent component can pass `DialogHolder` using Koin when creating child component (it should implement `KoinComponent` interface and call `get()` method)

The `DialogHolder` is also made available through Compose's `CompositionLocalProvider` for UI components through calling LocalDialogHolder.current getter:
```kotlin
val LocalDialogHolder = staticCompositionLocalOf<DialogHolder> {
    throw IllegalStateException("There is no DialogHolder in composables tree")
}

@Composable
fun RootPage(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val dialogSlot by component.dialog.subscribeAsState()

    CompositionLocalProvider(
        LocalDialogHolder provides component
    ) {
      // ...
    }
}
```

Finally, `DialogHolder` can be passed when creating child components as a callback closure `val onShowDialog: (DialogModel) -> Unit` or by itself. But when using this method, you need to pass the parameter throughout the component tree, which can take a significant amount of time.

## Showing Dialogs

Dialogs are shown by calling `dialogHolder.showDialog(model)` with a `DialogModel` instance:

```kotlin
// Toast example
override fun onShowToast() {
    scope.launch {
        dialogHolder.showDialog(
            DialogModel.Toast(
                message = getString(Res.string.dialogs_presenter_toast_message)
            )
        )
    }
}
```

> [!IMPORTANT]
> `CoroutineScope` is used to get string resource, so you can show dialogs without using `CoroutineScope`.
> To create `CorouteScope` for Component use corouteneScope() method from Decompose.

> [!WARNING]
> When using `CoroutineScope` to show dialog be sure to use `Dispatchers.Main` or you can deal with runtime crashes.

## Dialog Model Declaration

Dialog models are declared as sealed interface implementations of `DialogModel`:

```kotlin
@Immutable
class Toast(
  val message: String,
  val duration: Duration = 1.seconds,
  override val onDismiss: () -> Unit = {},
  override val isLocal: Boolean = true,
) : DialogModel
```
`isLocal` here indicates whether the dialog is **local** (will be closed upon navigation) or **global** (will **not** be closed upon navigation)

> [!IMPORTANT]
> Each `DialogModel` should be immutable and marked with `@Immutable` annotation to increase Compose performance.

## Adding New Models to Dialog Navigation

New dialog models are added to the dialog navigation by extending the `createDialog` factory method in `RootComponent`:

```kotlin
private fun createDialog(
    configuration: DialogModel,
    componentContext: ComponentContext,
): DialogComponent =
    when (configuration) {
        is DialogModel.InfoDialog -> DefaultInfoDialogComponent(
            componentContext = componentContext,
            onDismiss = ::onDismissDialog,
            infoDialog = configuration,
        )
        // Add new dialog types here
    }
```

## Callbacks

### onDismiss Callback
All dialog models have an `onDismiss` callback that's triggered when the dialog is dismissed by back navigation button and tapping outside of dialo. Also each dialog can select by itself when call onDismiss additionaly (for example by tap on special button). This callback means that dialog is ignored.

### Custom Callbacks
Each `DialogModel` can define custom callbacks that will be called by the dialog in special cases:

```kotlin
@Immutable
class ConfirmationDialog(
  val title: String,
  val message: String,
  val onConfirm: () -> Unit,
  val confirmTitle: String,
  val onCancel: () -> Unit = {},
  val cancelTitle: String?,
  override val onDismiss: () -> Unit = {},
  override val isLocal: Boolean = true,
) : DialogModel
```

The UI handles these callbacks in the confirmation pane [14](#0-13) .

## Auto-closing with StackNavigationComponent

The system automatically closes local dialogs when navigation occurs through the `subscribeOnWholeNavigation` mechanism. To keep it working, each navigation implementing Component should also implement `StackNavigationComponent` interface. So `RootComponent` will be able to detect navigation actions in the whole app and close dialogs.
This ensures that local dialogs (where `isLocal = true`) are automatically dismissed when users navigate to different screens.

## Notes

The system uses Decompose's `ChildSlot` for dialog management and prevents multiple dialogs from being shown simultaneously. The dialog rendering is handled in `RootPage` where different dialog types are mapped to their respective UI components.

## Dependencies

- **Compose Multiplatform**: UI framework
- **Decompose**: Navigation and component management
- **Koin**: Dependency injection to inject DialogHolder with DI instead of passing callback clo

## License

[MIT](https://choosealicense.com/licenses/mit/)

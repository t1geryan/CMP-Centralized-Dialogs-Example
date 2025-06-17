//
//  AppDelegate.swift
//  iosApp
//
//  Created by Tigran Movsisyan on 17.06.2025.
//

import SwiftUI
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate {
    lazy var root: RootComponent = DefaultRootComponent(
        componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle()),
        onMinimize: { }
    )
}

import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        MainViewControllerKt.doInitKoin()
    }
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var body: some Scene {
        WindowGroup {
            ComposeView(rootComponent: appDelegate.root)
        }
    }
}

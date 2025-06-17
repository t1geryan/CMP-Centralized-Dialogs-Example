package com.example.dialogs

import android.app.Application
import com.example.dialogs.di.commonModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                commonModules
            )
        }
    }
}
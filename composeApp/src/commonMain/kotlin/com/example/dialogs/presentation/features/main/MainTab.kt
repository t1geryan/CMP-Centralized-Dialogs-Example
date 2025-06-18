package com.example.dialogs.presentation.features.main

import androidx.compose.runtime.Immutable

@Immutable
enum class MainTab {
    PROFILE,
    SETTINGS;

    companion object {
        fun getByChild(child: MainComponent.Child) = when (child) {
            is MainComponent.Child.Profile -> PROFILE
            is MainComponent.Child.Settings -> SETTINGS
        }
    }
}
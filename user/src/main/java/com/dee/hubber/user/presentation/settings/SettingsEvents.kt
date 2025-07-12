package com.dee.hubber.user.presentation.settings

import com.dee.core_ui.base.AppEvent

sealed class SettingsEvents: AppEvent {
    data object onLogoutSuccess : SettingsEvents()
}
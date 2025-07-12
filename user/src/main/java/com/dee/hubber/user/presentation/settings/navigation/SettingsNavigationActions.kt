package com.dee.hubber.user.presentation.settings.navigation

import android.content.Context

interface SettingsNavigationActions {
    fun onLogout()
    fun onViewUserRepository(url: String, context: Context)
    fun onViewUserGithub(url: String, context: Context)
}
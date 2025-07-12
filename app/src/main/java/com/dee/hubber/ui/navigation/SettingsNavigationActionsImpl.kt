package com.dee.hubber.ui.navigation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.dee.hubber.user.presentation.settings.navigation.SettingsNavigationActions
import com.dee.hubber.utils.AuthEventManager

class SettingsNavigationActionsImpl(
    private val navController: NavController,
    private val authEventManager: AuthEventManager
) : SettingsNavigationActions {
    override fun onLogout() {
        authEventManager.emitTokenExpired()
    }

    override fun onViewUserRepository(url: String, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewUserGithub(url: String, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }
}